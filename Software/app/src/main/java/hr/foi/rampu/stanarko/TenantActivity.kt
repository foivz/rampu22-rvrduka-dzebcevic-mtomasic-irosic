package hr.foi.rampu.stanarko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import hr.foi.rampu.stanarko.NavigationDrawer.TenantDrawerActivity
import hr.foi.rampu.stanarko.database.MalfunctionsDAO
import hr.foi.rampu.stanarko.databinding.ActivityTenantBinding
import hr.foi.rampu.stanarko.entities.Tenant
import hr.foi.rampu.stanarko.helpers.FirebaseNotifications
import hr.foi.rampu.stanarko.helpers.MockDataLoader
import hr.foi.rampu.stanarko.helpers.NewMalfunctionDialogHelper
import kotlinx.coroutines.runBlocking

class TenantActivity : TenantDrawerActivity() {

    lateinit var status: TextView
    lateinit var malfunctionButton: Button
    lateinit var binding: ActivityTenantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocatedActivityTitle("TenantActivity")
        val userMail = FirebaseAuth.getInstance().currentUser?.email
        val user = runBlocking { MockDataLoader.getTenantByMail(userMail!!) }
        malfunctionButton = findViewById(R.id.btn_malfunction)
        malfunctionButton.visibility = View.GONE
        if (user.flat != null) {
            status = findViewById(R.id.tv_belongs_to_flat)
            status.text = buildString {
                append(getString(R.string.flat_from))
                append(" ")
                append(user.flat.owner!!.name)
                append(" ")
                append(user.flat.owner.surname)
                malfunctionButton.visibility = View.VISIBLE
                malfunctionButton.setOnClickListener {
                    showMalfunction(user)
                }
            }
        }
    }

    private fun showMalfunction(tenant: Tenant) {
        val newMalfunctionReportView =
            LayoutInflater
                .from(this)
                .inflate(R.layout.malfunction_report, null)

        AlertDialog.Builder(this)
            .setView(newMalfunctionReportView)
            .setTitle(getString(R.string.report_a_malfunction))
            .setPositiveButton(getString(R.string.report)) { _,_ ->
                val helper = NewMalfunctionDialogHelper(newMalfunctionReportView)
                val malfunction = helper.buildMalfunction(tenant)
                val malfunctionDAO = MalfunctionsDAO()
                malfunctionDAO.addMalfunction(malfunction, this)
                val notification = FirebaseNotifications()
                notification.sendPushNotification(tenant.flat!!.owner!!.token, getString(R.string.malfunction_notification_title), getString(
                                    R.string.malfunction_reported))
            }
            .show()
    }
}