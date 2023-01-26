package hr.foi.rampu.stanarko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import hr.foi.rampu.stanarko.NavigationDrawer.OwnerDrawerActivity
import hr.foi.rampu.stanarko.NavigationDrawer.TenantDrawerActivity
import hr.foi.rampu.stanarko.database.RentsDAO
import hr.foi.rampu.stanarko.fragments.PaidRentFragment
import hr.foi.rampu.stanarko.fragments.UnpaidRentFragment
import hr.foi.rampu.stanarko.adapters.RentManagerAdapter
import hr.foi.rampu.stanarko.databinding.ActivityRentManagerBinding

class RentManagerActivity : TenantDrawerActivity() {
    private lateinit var binding: ActivityRentManagerBinding

    private lateinit var viewPager2: ViewPager2

    private var rentsDAO = RentsDAO()

    override fun onStart() {
        super.onStart()
        rentsDAO.checkForRents()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_rent_manager)

        binding = ActivityRentManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocatedActivityTitle("Rents")

        val currentUserMail = intent.getStringExtra("mail")
        Log.w("MAIL", currentUserMail.toString())

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
        val intent = Intent(this, TenantActivity::class.java)
        startActivity(intent)
        finish()
    }
}