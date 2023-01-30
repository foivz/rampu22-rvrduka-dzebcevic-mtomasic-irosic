package hr.foi.rampu.stanarko

import android.os.Bundle
import hr.foi.rampu.stanarko.NavigationDrawer.OwnerDrawerActivity
import hr.foi.rampu.stanarko.databinding.ActivityOwnerContractManagerBinding

class OwnerContractManagerActivity : OwnerDrawerActivity() {

    lateinit var binding: ActivityOwnerContractManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerContractManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("Owner contracts")
    }
}