package hr.foi.rampu.stanarko.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.entities.Contract
import hr.foi.rampu.stanarko.entities.Tenant
import java.util.*

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
        val db = FirebaseFirestore.getInstance()
        val newContractDialogView = LayoutInflater.from(context).inflate(R.layout.new_contract_dialog,null)
        val etTenantMail = newContractDialogView.findViewById<EditText>(R.id.et_contract_tenant_email)
        val etExpiredDate = newContractDialogView.findViewById<CalendarView>(R.id.et_contract_expiring_date)

        var selectedDate = Calendar.getInstance().time

        etExpiredDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.time
        }

        AlertDialog.Builder(context).setView(newContractDialogView).setTitle(getString(R.string.generate_new_contract_dialog))
            .setPositiveButton(getString(R.string.generate_new_contract_dialog)){ _, _ ->
                if (!TextUtils.isEmpty(etTenantMail.text.toString().trim())&&etExpiredDate.date!=0L){
                    db.collection("tenants").whereEqualTo("mail",etTenantMail.text.toString()).get()
                        .addOnSuccessListener { documents ->
                            for (document in documents){
                                val tenant = document.toObject(Tenant::class.java)
                                val contractsRef = db.collection("contracts")
                                contractsRef.add(Contract(Calendar.getInstance().time, selectedDate,tenant))
                            }
                        }
                }
            }
            .show()
    }
}