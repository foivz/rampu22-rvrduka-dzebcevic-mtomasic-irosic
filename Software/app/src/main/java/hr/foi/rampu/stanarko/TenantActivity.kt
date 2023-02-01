package hr.foi.rampu.stanarko

import MalfunctionAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import hr.foi.rampu.stanarko.NavigationDrawer.TenantDrawerActivity
import hr.foi.rampu.stanarko.adapters.FlatsAdapter
import hr.foi.rampu.stanarko.database.MalfunctionsDAO
import hr.foi.rampu.stanarko.databinding.ActivityTenantBinding
import hr.foi.rampu.stanarko.entities.Malfunction
import hr.foi.rampu.stanarko.helpers.MockDataLoader
import kotlinx.coroutines.*

class TenantActivity : TenantDrawerActivity() {

    lateinit var status: TextView
    lateinit var malfunctionButton: Button
    private lateinit var recyclerView: RecyclerView

    lateinit var binding: ActivityTenantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocatedActivityTitle("TenantActivity")


        val userMail = FirebaseAuth.getInstance().currentUser?.email
        val user = runBlocking { MockDataLoader.getTenantByMail(userMail!!) }
        malfunctionButton = findViewById(R.id.btn_malfunction)
        malfunctionButton.visibility = View.GONE
        if (user.flat != null) {


            status = findViewById(R.id.tv_belongs_to_flat)
            status.text = buildString {
                append(getString(R.string.flat_from))
                append(" ")
                append(user.flat.owner!!.name)
                append(" ")
                append(user.flat.owner.surname)
                malfunctionButton.visibility = View.VISIBLE
                malfunctionButton.setOnClickListener {

                }
            }
            recyclerView = findViewById(R.id.rv_malfunction_list)
            recyclerView.adapter = runBlocking { MalfunctionAdapter(MockDataLoader.getFirebaseMalfunctions()) }
            recyclerView.layoutManager = LinearLayoutManager(this)

        }


    }



}