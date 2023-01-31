package hr.foi.rampu.stanarko.adapters

import android.content.Context
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
import com.google.firebase.firestore.Query
import hr.foi.rampu.stanarko.ChatActivity
import hr.foi.rampu.stanarko.ChatActivityOwner
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.database.ChannelsDAO
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.entities.Channel
import kotlinx.coroutines.runBlocking

class ChannelAdapter(
    query: Query,
    private val context: Context,
    modelClass: Class<Channel>,
    private val onCreatedConversation: ((taskId: Int, dueMonth: Int, dueYear: Int) -> Unit)? = null) :
    FirestoreRecyclerAdapter<Channel, ChannelAdapter.ViewHolder>(
        FirestoreRecyclerOptions.Builder<Channel>()
            .setQuery(query, modelClass)
            .build()
    ) {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val currentUserMail = currentUser?.email.toString()
    private val channelsDAO = ChannelsDAO()
    private val tenantsDAO = TenantsDAO()

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Channel) {
        if (position < itemCount) {
            holder.bind(model)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.channel_item, parent, false)
        return ViewHolder(view)
    }

    override fun onDataChanged() {
        super.onDataChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewChannelName : TextView

        init {
            textViewChannelName = itemView.findViewById(R.id.textViewChannelName)

            itemView.setOnClickListener {
                val channel = snapshots.getSnapshot(adapterPosition)
                val isTenant = runBlocking { tenantsDAO.isUserTenant(currentUserMail) }
                Log.w("ISTENANT", isTenant.toString())
                var intent: Intent = if(isTenant){
                    Intent(itemView.context, ChatActivity::class.java)
                }else{
                    Intent(itemView.context, ChatActivityOwner::class.java)
                }

                intent.putExtra("channel", channel.id)
                intent.putExtra("chatPartner",
                    channel.toObject(Channel::class.java)?.let { ch -> getChatPartner(ch) })

                itemView.context.startActivity(intent)
            }
        }

        fun bind(channel: Channel) {
            textViewChannelName.text = getChatPartner(channel)
        }

        private fun getChatPartner(channel: Channel): String{
            val participants = runBlocking { channelsDAO.participantsNameSurname(channel) }

            return if(channel.participants[0] == currentUserMail){
                participants[1]
            }else{
                participants[0]
            }
        }
    }
}