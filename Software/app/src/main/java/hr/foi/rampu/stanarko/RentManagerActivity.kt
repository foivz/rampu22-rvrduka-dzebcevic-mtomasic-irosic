package hr.foi.rampu.stanarko

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import hr.foi.rampu.stanarko.NavigationDrawer.TenantDrawerActivity
import hr.foi.rampu.stanarko.fragments.PaidRentFragment
import hr.foi.rampu.stanarko.fragments.UnpaidRentFragment
import hr.foi.rampu.stanarko.adapters.RentManagerAdapter
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.databinding.ActivityRentManagerBinding
import kotlinx.coroutines.runBlocking

class RentManagerActivity : TenantDrawerActivity() {
    private val currentUserMail = currentUser?.email.toString()
    private val tenantsDAO = TenantsDAO()

    private lateinit var binding: ActivityRentManagerBinding

    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRentManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocatedActivityTitle("Rents")

        val rentPagerAdapter = RentManagerAdapter (supportFragmentManager, lifecycle)
        rentPagerAdapter.addFragment(
            RentManagerAdapter.FragmentItem(
                R.string.unpaid_rent,
                UnpaidRentFragment::class
            )
        )

        rentPagerAdapter.addFragment(
            RentManagerAdapter.FragmentItem(
                R.string.paid_rent,
                PaidRentFragment::class
            )
        )

        viewPager2 = findViewById(R.id.viewpager)
        viewPager2.adapter = rentPagerAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val isTenant = runBlocking { tenantsDAO.isUserTenant(currentUserMail) }
        val intent: Intent = if(isTenant){
            Intent(this, TenantActivity::class.java)
        }else{
            Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}