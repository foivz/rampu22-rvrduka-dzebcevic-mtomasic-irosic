package hr.foi.rampu.stanarko

import android.os.Bundle
import hr.foi.rampu.stanarko.NavigationDrawer.OwnerDrawerActivity
import hr.foi.rampu.stanarko.databinding.ActivityOwnerContractManagerBinding
import hr.foi.rampu.stanarko.fragments.OwnerActiveContractsFragment
import hr.foi.rampu.stanarko.fragments.OwnerExpiredContractsFragment
import hr.foi.rampu.teststanarko.adapters.RentManagerAdapter

class OwnerContractManagerActivity : OwnerDrawerActivity() {

    lateinit var binding: ActivityOwnerContractManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerContractManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("Owner contracts")

        val contractAdapter = RentManagerAdapter (supportFragmentManager, lifecycle)
        contractAdapter.addFragment(
            RentManagerAdapter.FragmentItem(
                R.string.owner_active_contract_fragment,
                OwnerActiveContractsFragment::class
            )
        )

        contractAdapter.addFragment(
            RentManagerAdapter.FragmentItem(
                R.string.owner_expired_contract_fragment,
                OwnerExpiredContractsFragment::class
            )
        )
    }
}