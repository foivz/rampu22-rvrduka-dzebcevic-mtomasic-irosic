package hr.foi.rampu.stanarko.NavigationDrawer

import android.content.Intent
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
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.stanarko.F02_Prijava.Prijava
import hr.foi.rampu.stanarko.TenantActivity
import hr.foi.rampu.stanarko.TenantContractManagerActivity
import hr.foi.rampu.stanarko.TenantMovingOutActivity
import hr.foi.rampu.stanarko.entities.Tenant

open class TenantDrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
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
                val userMail = FirebaseAuth.getInstance().currentUser!!.email
                FirebaseFirestore.getInstance().collection("tenants").whereEqualTo("mail",userMail).get()
                    .addOnSuccessListener { documents ->
                        if(documents.size()>0){
                            val document = documents.first().toObject(Tenant::class.java)
                            if (document.flat!=null){
                                val intent = Intent(this,TenantMovingOutActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(baseContext, "You don't have a flat", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }
        return false
    }

    protected fun allocatedActivityTitle(title: String){
        supportActionBar?.let { it.title = title }
    }
}