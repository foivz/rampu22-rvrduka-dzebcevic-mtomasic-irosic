package hr.foi.rampu.stanarko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.foi.rampu.stanarko.NavigationDrawer.TenantDrawerActivity
import hr.foi.rampu.stanarko.databinding.ActivityTenantBinding

class TenantActivity : TenantDrawerActivity() {
    lateinit var binding: ActivityTenantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocatedActivityTitle("TenantActivity")
    }
}