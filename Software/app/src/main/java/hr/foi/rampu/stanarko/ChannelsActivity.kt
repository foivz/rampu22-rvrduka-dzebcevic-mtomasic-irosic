package hr.foi.rampu.stanarko

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.stanarko.NavigationDrawer.OwnerDrawerActivity
import hr.foi.rampu.stanarko.adapters.ChannelAdapter
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.databinding.ActivityChannelsBinding
import hr.foi.rampu.stanarko.databinding.ActivityChatBinding
import hr.foi.rampu.stanarko.entities.Channel
import kotlinx.coroutines.runBlocking

class ChannelsActivity : OwnerDrawerActivity() {
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val currentUserMail = currentUser?.email.toString()
    private val tenantsDAO = TenantsDAO()

    private lateinit var binding: ActivityChannelsBinding

    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: ChannelAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnCreateConversation: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChannelsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("All tenants")

        db = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.rv_channels)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val query = db.collection("channels").whereNotEqualTo("participants", null).whereArrayContains("participants", currentUserMail)
        val options = FirestoreRecyclerOptions.Builder<Channel>()
            .setQuery(query, Channel::class.java)
            .build()
        adapter = ChannelAdapter(options)
        recyclerView.adapter = adapter

        btnCreateConversation = findViewById(R.id.fab_create_conversation)
        btnCreateConversation.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("Create conversation")
                .setNeutralButton("Start conversation") { _, _ ->

                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onResume() {
        super.onResume()
        recyclerView.recycledViewPool.clear()
        adapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
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