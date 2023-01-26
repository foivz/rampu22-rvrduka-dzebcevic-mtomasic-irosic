package hr.foi.rampu.stanarko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hr.foi.rampu.stanarko.NavigationDrawer.TenantDrawerActivity
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.adapters.ChatAdapter
import hr.foi.rampu.stanarko.databinding.ActivityChatBinding
import hr.foi.rampu.stanarko.databinding.ActivityRentManagerBinding
import hr.foi.rampu.stanarko.entities.Channel
import hr.foi.rampu.stanarko.entities.Chat
import java.util.*

class ChatActivity : TenantDrawerActivity() {
    var currentUser = FirebaseAuth.getInstance().currentUser

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
        //val userIMessage = findViewById<TextView>(R.id.tv_person_i_message)

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
            db.collection("channels").document(channelId.toString()).collection("messages").add(chat)
            //db.collection("chats").add(chat)
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
}