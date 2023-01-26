package hr.foi.rampu.stanarko.adapters

import android.content.Intent
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
import hr.foi.rampu.stanarko.entities.Channel

class ChannelAdapter(options: FirestoreRecyclerOptions<Channel>) :
    FirestoreRecyclerAdapter<Channel, ChannelAdapter.ViewHolder>(options) {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val currentUserMail = currentUser?.email.toString()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewChannelName : TextView

        init {
            textViewChannelName = itemView.findViewById(R.id.textViewChannelName)

            itemView.setOnClickListener {
                //val currentParent = (itemView.parent) as ViewGroup
                //currentParent.removeAllViews()

                val channel = getSnapshots().getSnapshot(adapterPosition)
                val intent = Intent(itemView.context, ChatActivity::class.java)
                intent.putExtra("channel", channel.id)
                itemView.context.startActivity(intent)
            }
        }

        fun bind(channel: Channel) {
            if (channel.participants.size < 2) {
                return
            }

            if(channel.participants[0] == currentUserMail){
                textViewChannelName.text = "${channel.participants[1]}"
            }else{
                textViewChannelName.text = "${channel.participants[0]}"
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