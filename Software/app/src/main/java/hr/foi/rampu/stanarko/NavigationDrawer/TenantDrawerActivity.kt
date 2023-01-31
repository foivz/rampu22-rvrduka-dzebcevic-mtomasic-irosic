package hr.foi.rampu.stanarko.NavigationDrawer

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import hr.foi.rampu.stanarko.*
import hr.foi.rampu.stanarko.F02_Prijava.Prijava
import hr.foi.rampu.stanarko.database.ChannelsDAO
import hr.foi.rampu.stanarko.database.OwnersDAO
import hr.foi.rampu.stanarko.database.TenantsDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

open class TenantDrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var currentUser = FirebaseAuth.getInstance().currentUser
    private val currentUserMail = currentUser?.email.toString()
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
            R.id.menu_rents_tenant -> {
                val intent = Intent(this, RentManagerActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("mail", currentUser?.email)
                startActivity(intent)
            }
            R.id.menu_chat_tenant -> {
                val channelsDAO = ChannelsDAO()
                val tenantsDAO = TenantsDAO()
                val ownersDAO = OwnersDAO()

                var isUserInFlat = runBlocking { tenantsDAO.isUserInFlat(currentUserMail) }
                if(isUserInFlat){
                    val isThereAChannelWithOwner = runBlocking { channelsDAO.isThereChannelWithOwner(currentUserMail) }
                    if(!isThereAChannelWithOwner){
                        runBlocking { channelsDAO.createNewChannel(currentUserMail) }
                    }

                    val channelID = runBlocking {channelsDAO.getChannelID(currentUserMail)}
                    val intent = Intent(this, ChatActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("channel", channelID)
                    startActivity(intent)

                }else{
                    Toast.makeText(this,"You have to wait to be added in flat to be able to talk your landlord",Toast.LENGTH_LONG).show()
                }
            }
            R.id.menu_tenant_dateOfMove ->{


                val c = Calendar.getInstance()
                var year = c.get(Calendar.YEAR)
                var month = c.get(Calendar.MONTH)
                var day = c.get(Calendar.DAY_OF_MONTH)
                var selectedDate = ""


                val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    // handle the selected date
                    var dan = "0"
                    var mjesec = "0"
                    if(dayOfMonth<10){
                        dan+=dayOfMonth.toString()
                    }
                    else{
                        dan = dayOfMonth.toString()
                    }
                    if(monthOfYear < 10){
                        mjesec+=(monthOfYear+1).toString()
                    }
                    else{
                        mjesec = (monthOfYear+1).toString()
                    }

                    val selectedDate = "$year/$mjesec/$dan"
                    // use the selected date
                    var help = TenantsDAO()
                    help.changeDateOfMovingIn(currentUserMail, selectedDate)
                }

                val datePickerDialog = DatePickerDialog(this, dateSetListener, year, month, day)
                datePickerDialog.show()

            }

            R.id.menu_tenant_contracts -> {
                val intent = Intent(this,TenantContractManagerActivity::class.java)
                startActivity(intent)
            }

        }
        return false
    }

    protected fun allocatedActivityTitle(title: String){
        supportActionBar?.let { it.title = title }
    }
}