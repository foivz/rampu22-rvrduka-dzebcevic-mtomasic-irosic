package hr.foi.rampu.stanarko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import hr.foi.rampu.stanarko.NavigationDrawer.TenantDrawerActivity
import hr.foi.rampu.stanarko.databinding.ActivityTenantBinding
import hr.foi.rampu.stanarko.helpers.MockDataLoader
import kotlinx.coroutines.runBlocking

class TenantActivity : TenantDrawerActivity() {

    lateinit var status: TextView
    lateinit var binding: ActivityTenantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocatedActivityTitle("TenantActivity")
        val userMail = FirebaseAuth.getInstance().currentUser?.email
        val user = runBlocking { MockDataLoader.getTenantByMail(userMail!!) }
        if (user.flat != null) {
            status = findViewById(R.id.tv_belongs_to_flat)
            status.text = buildString {
                append(getString(R.string.flat_from))
                append(" ")
                append(user.flat.owner!!.name)
                append(" ")
                append(user.flat.owner.surname)
            }
        }
    }
}