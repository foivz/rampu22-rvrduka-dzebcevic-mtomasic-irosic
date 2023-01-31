package hr.foi.rampu.stanarko

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import hr.foi.rampu.stanarko.NavigationDrawer.TenantDrawerActivity
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.databinding.ActivityTenantBinding
import hr.foi.rampu.stanarko.entities.Tenant
import java.util.Calendar

class TenantActivity : TenantDrawerActivity() {
    lateinit var binding: ActivityTenantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocatedActivityTitle("TenantActivity")

        dayOfMovingOutCheck()

    }

    private fun dayOfMovingOutCheck() {
        val tenant = TenantsDAO()
        val userMail = FirebaseAuth.getInstance().currentUser!!.email
        val today = Calendar.getInstance().time

        tenant.getTenantByMail(userMail!!).addOnSuccessListener {
            if (!it.isEmpty){
                val document = it.documents.first().toObject(Tenant::class.java)
                if(document!!.dateOfMovingOut!=null){
                    if (document.dateOfMovingOut!!.before(today)){
                        val data = it.documents.first()
                        data.reference.update("flat", FieldValue.delete())
                        data.reference.update("flat",null)
                        data.reference.update("dateOfMovingOut",null)
                    }
                }
            }
        }
    }
}