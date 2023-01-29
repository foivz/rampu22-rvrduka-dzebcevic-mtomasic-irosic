package hr.foi.rampu.stanarko.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import hr.foi.rampu.stanarko.ChatActivity
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.database.ChannelsDAO
import hr.foi.rampu.stanarko.entities.Channel
import kotlinx.coroutines.runBlocking

class ChannelAdapter(options: FirestoreRecyclerOptions<Channel>) :
    FirestoreRecyclerAdapter<Channel, ChannelAdapter.ViewHolder>(options) {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val currentUserMail = currentUser?.email.toString()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewChannelName : TextView

        init {
            textViewChannelName = itemView.findViewById(R.id.textViewChannelName)

            itemView.setOnClickListener {
                val channel = getSnapshots().getSnapshot(adapterPosition)
                val intent = Intent(itemView.context, ChatActivity::class.java)
                intent.putExtra("channel", channel.id)
                itemView.context.startActivity(intent)
            }
        }

        fun bind(channel: Channel) {
            val channelsDAO = ChannelsDAO()
            val participants = runBlocking { channelsDAO.participantsNameSurname(channel) }

            if (channel.participants.size < 2) {
                return
            }

            if(channel.participants[0] == currentUserMail){
                textViewChannelName.text = participants[1]
            }else{
                textViewChannelName.text = participants[0]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.channel_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Channel) {
        if (position < itemCount) {
            holder.bind(model)
        }
    }
}