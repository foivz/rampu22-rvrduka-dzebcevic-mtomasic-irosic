package hr.foi.rampu.stanarko.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.foi.rampu.stanarko.R

class OwnerActiveContractsFragment : Fragment() {

    private lateinit var btnCreateContract: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_owner_active_contracts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnCreateContract = view.findViewById(R.id.create_new_contract_floating_button)
        btnCreateContract.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val newContractDialogView = LayoutInflater.from(context).inflate(R.layout.new_contract_dialog,null)
        AlertDialog.Builder(context).setView(newContractDialogView).setTitle("Generate new contract").show()
    }
}