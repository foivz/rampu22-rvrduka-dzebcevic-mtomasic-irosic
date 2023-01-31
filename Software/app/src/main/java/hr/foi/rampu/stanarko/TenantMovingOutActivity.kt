package hr.foi.rampu.stanarko

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.stanarko.NavigationDrawer.TenantDrawerActivity
import hr.foi.rampu.stanarko.databinding.ActivityTenantMovingOutBinding
import hr.foi.rampu.stanarko.entities.Tenant
import java.text.SimpleDateFormat
import java.util.*

class TenantMovingOutActivity : TenantDrawerActivity() {

    private  lateinit var binding: ActivityTenantMovingOutBinding
    private lateinit var btnSaveDate: Button
    private lateinit var tvMovingDate: TextView
    private lateinit var cvMovingOut: CalendarView
    private lateinit var dateFormat: SimpleDateFormat
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTenantMovingOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocatedActivityTitle("Moving out")

        btnSaveDate = findViewById(R.id.btn_save_moving_out)
        tvMovingDate = findViewById(R.id.tv_moving_out)
        cvMovingOut = findViewById(R.id.cv_tenant_moving_out)
        dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val userMail = FirebaseAuth.getInstance().currentUser!!.email

        saveDateToDataBase(userMail)
        loadDataToTextView(userMail)
    }

    private fun loadDataToTextView(userMail: String?) {
        db.collection("tenants").whereEqualTo("mail",userMail).get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val contract: Tenant? =data.toObject(Tenant::class.java)
                        if(contract!!.dateOfMovingOut!=null){
                            Log.e("------------------------------","posotji date of moving out i ovo je format koji hocu " + dateFormat)
                            tvMovingDate.text = dateFormat.format(contract.dateOfMovingOut!!)
                        } else {
                            tvMovingDate.text = getString(R.string.date_of_moving_out_doesnt_exist_message)
                        }
                    }
                }
            }
            .addOnFailureListener{ e ->
                Log.e("Error message: ","Couldn't retrieve user")
            }
    }
    private fun saveDateToDataBase(userMail: String?) {
        var selectedDate :Date

        selectedDate = Calendar.getInstance().time
        cvMovingOut.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.time
        }
        btnSaveDate.setOnClickListener {
            FirebaseFirestore.getInstance().collection("tenants").whereEqualTo("mail",userMail).get()
                .addOnSuccessListener { documents ->
                    if(documents.size()>0){
                        val document = documents.first()
                        document.reference.update("dateOfMovingOut",selectedDate)
                            .addOnSuccessListener {
                                tvMovingDate.text = dateFormat.format(selectedDate)
                                Log.e("--------------------------------------","Uspijesno dodan: "+selectedDate)
                            }
                            .addOnFailureListener { e->
                                Toast.makeText(this.baseContext, "Error: "+e.message, Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e->
                    Toast.makeText(this.baseContext, "Error: "+e.message, Toast.LENGTH_SHORT).show()
                }
        }
    }
}