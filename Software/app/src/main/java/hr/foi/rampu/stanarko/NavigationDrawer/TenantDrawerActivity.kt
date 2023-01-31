package hr.foi.rampu.stanarko.NavigationDrawer

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import hr.foi.rampu.stanarko.R
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import hr.foi.rampu.stanarko.F02_Prijava.Prijava
import hr.foi.rampu.stanarko.TenantActivity
import hr.foi.rampu.stanarko.TenantContractManagerActivity
import hr.foi.rampu.stanarko.TenantMovingOutActivity
import hr.foi.rampu.stanarko.database.RentsDAO
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.entities.Tenant
import kotlinx.coroutines.runBlocking
import java.util.Calendar

open class TenantDrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    override fun setContentView(view: View?) {
        drawerLayout = layoutInflater.inflate(R.layout.activity_tenant_drawer, null) as DrawerLayout
        val container: FrameLayout = drawerLayout.findViewById(R.id.activity_container)
        container.addView(view)
        super.setContentView(drawerLayout)

        val toolbar: Toolbar = drawerLayout.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navView: NavigationView = drawerLayout.findViewById(R.id.nav_view_tenant)
        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_log_out_tenant -> {
                var currentUser = FirebaseAuth.getInstance().currentUser
                if(currentUser!=null){
                    FirebaseAuth.getInstance().signOut()
                    currentUser = FirebaseAuth.getInstance().currentUser
                    if(currentUser== null){
                        val intent = Intent(this, Prijava::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,getString(R.string.failed_to_log_out_message), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.menu_tenant_contracts -> {
                val intent = Intent(this,TenantContractManagerActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tenant_main_activity -> {
                val intent = Intent(this,TenantActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tenant_moving_out ->{
                movingOutMenuFunction()
            }
        }
        return false
    }

    private fun movingOutMenuFunction() {
        val today = Calendar.getInstance().time
        val userMail = FirebaseAuth.getInstance().currentUser?.email
        val tenant = TenantsDAO()

        if (userMail != null) {
            tenant.getTenantByMail(userMail).addOnSuccessListener {
                if (!it.isEmpty){
                    val document = it.documents.first().toObject(Tenant::class.java)
                    if (document!!.flat!=null){
                        if (document.dateOfMovingOut!=null){
                            val rents = runBlocking { RentsDAO().getAllRentsByTenantMail(userMail,paid = false) }
                            if (!rents!!.isEmpty){
                                redirectToTenantMovingOut()
                                Toast.makeText(baseContext, "You need to pay off all your rents first", Toast.LENGTH_SHORT).show()
                            } else if (document.dateOfMovingOut.before(today)){
                                val data = it.documents.first()
                                data.reference.update("flat",FieldValue.delete())
                                data.reference.update("flat",null)
                                data.reference.update("dateOfMovingOut",null)
                            }else{
                                redirectToTenantMovingOut()
                            }
                        }else{
                            redirectToTenantMovingOut()
                        }
                    }else{
                        Toast.makeText(baseContext, "You don't have a flat", Toast.LENGTH_SHORT).show()
                    }
                }

            }
                .addOnFailureListener {
                    Log.e("DATA","Neuspijesno dohvacanje tenanta")
                }
        }
    }

    private fun redirectToTenantMovingOut(){
        val intent = Intent(this,TenantMovingOutActivity::class.java)
        startActivity(intent)
    }
    protected fun allocatedActivityTitle(title: String){
        supportActionBar?.let { it.title = title }
    }
}
