package hr.foi.rampu.stanarko.helpers

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import hr.foi.rampu.stanarko.MainActivity
import hr.foi.rampu.stanarko.TenantActivity
import hr.foi.rampu.stanarko.database.TenantsDAO
import kotlinx.coroutines.runBlocking

class HelperClass {
    val tenantsDAO = TenantsDAO()

    fun navigateToNextScreen(context: Context, currentUserMail: String) {
        val isTenant = runBlocking { tenantsDAO.isUserTenant(currentUserMail) }
        val intent: Intent = if(isTenant) {
            Intent(context, TenantActivity::class.java)
        } else {
            Intent(context, MainActivity::class.java)
        }
        context.startActivity(intent)
    }
}