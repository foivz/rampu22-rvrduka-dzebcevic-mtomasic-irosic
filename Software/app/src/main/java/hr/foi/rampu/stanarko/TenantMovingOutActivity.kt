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
import java.text.SimpleDateFormat
import java.util.*

class TenantMovingOutActivity : TenantDrawerActivity() {

    private  lateinit var binding: ActivityTenantMovingOutBinding
    private lateinit var btnSaveDate: Button
    private lateinit var tvMovingDate: TextView
    private lateinit var dateFormat: SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTenantMovingOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocatedActivityTitle("Moving out")



        btnSaveDate = findViewById(R.id.btn_save_moving_out)
        tvMovingDate = findViewById(R.id.tv_moving_out)
        dateFormat = SimpleDateFormat("dd.MM.yyyy")
        var userMail = FirebaseAuth.getInstance().currentUser!!.email

        saveDateToDataBase(userMail)

    }

    private fun saveDateToDataBase(userMail: String?) {
        var selectedDate :Date
        val cvMovingOut = findViewById<CalendarView>(R.id.cv_tenant_moving_out)

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