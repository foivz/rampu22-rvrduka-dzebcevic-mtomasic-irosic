package hr.foi.rampu.stanarko

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import hr.foi.rampu.stanarko.NavigationDrawer.OwnerDrawerActivity
import hr.foi.rampu.stanarko.adapters.RentManagerAdapter
import hr.foi.rampu.stanarko.database.RentsDAO
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.databinding.ActivityRentManagerOwnerBinding
import hr.foi.rampu.stanarko.fragments.PaidRentFragment
import hr.foi.rampu.stanarko.fragments.UnpaidRentFragment
import kotlinx.coroutines.runBlocking

class RentManagerActivityOwner : OwnerDrawerActivity() {
    private val currentUserMail = currentUser?.email.toString()
    private val tenantsDAO = TenantsDAO()
    private var rentsDAO = RentsDAO()

    private lateinit var binding: ActivityRentManagerOwnerBinding

    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRentManagerOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("Rents")

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


        val btnCheckForRents = findViewById<Button>(R.id.btn_check_for_rents)
        btnCheckForRents.setOnClickListener{
            rentsDAO.checkForRents()
            Toast.makeText(this,"Check for rents is over!", Toast.LENGTH_LONG).show()
        }

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