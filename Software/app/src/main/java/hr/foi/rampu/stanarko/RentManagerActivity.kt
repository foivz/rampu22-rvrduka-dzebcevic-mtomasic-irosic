package hr.foi.rampu.stanarko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import hr.foi.rampu.stanarko.database.RentsDAO
import hr.foi.rampu.stanarko.fragments.PaidRentFragment
import hr.foi.rampu.stanarko.fragments.UnpaidRentFragment
import hr.foi.rampu.teststanarko.adapters.RentManagerAdapter

class RentManagerActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private var rentsDAO = RentsDAO()

    override fun onStart() {
        super.onStart()
        rentsDAO.checkForRents()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_manager)

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
}