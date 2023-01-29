package hr.foi.rampu.stanarko

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hr.foi.rampu.stanarko.NavigationDrawer.TenantDrawerActivity
import hr.foi.rampu.stanarko.adapters.ChatAdapter
import hr.foi.rampu.stanarko.database.ChannelsDAO
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.databinding.ActivityChatBinding
import hr.foi.rampu.stanarko.entities.Channel
import hr.foi.rampu.stanarko.entities.Chat
import kotlinx.coroutines.runBlocking
import java.util.*

class ChatActivity : TenantDrawerActivity() {
    private val currentUserMail = currentUser?.email.toString()
    private val tenantsDAO = TenantsDAO()
    private val channelsDAO = ChannelsDAO()

    private lateinit var binding: ActivityChatBinding

    private lateinit var db: FirebaseFirestore
    private lateinit var query: Query
    private lateinit var adapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocatedActivityTitle("Owner")

        db = FirebaseFirestore.getInstance()
        val channelId = intent.getStringExtra("channel")
        val currentUserMail = currentUser?.email.toString()

        if(channelId!=null){
            query = FirebaseFirestore.getInstance().collection("channels").document(channelId.toString()).collection("messages")
                .whereNotEqualTo("timestamp", "")
                .orderBy("timestamp")
        }

        recyclerView = findViewById(R.id.rv_chats)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ChatAdapter(query, this, Chat::class.java)
        recyclerView.adapter = adapter

        val sendButton = findViewById<ImageButton>(R.id.buttonSend)
        val messageEditText = findViewById<EditText>(R.id.editTextMessage)

        FirebaseFirestore.getInstance().collection("channels").document(channelId.toString()).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("Listen failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val selectedChannel = snapshot.toObject(Channel::class.java)
                //userIMessage.text = selectedChannel!!.participants[0]
                if (selectedChannel != null) {
                    if(selectedChannel.participants[0] == currentUserMail){
                        //userIMessage.text = selectedChannel.participants[1]
                    }
                }
            }
        }

        sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString()
            val chat = Chat(currentUserMail, messageText, Date())
            runBlocking { channelsDAO.addNewMessage(channelId.toString(), chat) }
            messageEditText.text.clear()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
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
            Intent(this, ChannelsActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}