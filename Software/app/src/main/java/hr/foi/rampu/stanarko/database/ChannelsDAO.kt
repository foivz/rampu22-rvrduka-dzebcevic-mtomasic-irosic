package hr.foi.rampu.stanarko.database

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hr.foi.rampu.stanarko.entities.Channel
import hr.foi.rampu.stanarko.entities.Chat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*

class ChannelsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val ownersDAO = OwnersDAO()
    suspend fun getChannel(id: String?) : Channel? {
        val rentsRef = db.collection("channels")
            .whereArrayContains("id", id.toString())
            .get().await()
        val documents = rentsRef.documents
        return documents[0].toObject(Channel::class.java)
    }

    fun getMessageQuery(channelId: String): Query {
        return db.collection("channels").document(channelId).collection("messages")
            .whereNotEqualTo("timestamp", "")
            .orderBy("timestamp")
    }

    suspend fun getChannelID(mail: String?) : String {
        val channelRef = db.collection("channels")
            .whereArrayContains("participants", mail.toString())
            .get()
            .await()
        val documents = channelRef.documents
        return documents[0].id
    }

    suspend fun updateChannelID(channelID: String){
        db.collection("channels").document(channelID).update("id", channelID).await()
        db.collection("channels").document(channelID).update("messages", FieldValue.delete()).await()
    }

    suspend fun addNewMessage(channelID: String){
        val emptyMessage = hashMapOf<String, Any>()
        db.collection("channels")
            .document(channelID).collection("messages")
            .add(emptyMessage).await()
    }

    suspend fun addNewMessage(channelID: String, message: Chat){
        db.collection("channels")
            .document(channelID)
            .collection("messages")
            .add(message).await()
    }

    suspend fun isThereChannelWithOwner(mail: String?) : Boolean{
        var tenants = db.collection("channels")
            .whereArrayContains("participants", mail.toString())
            .get().await()
        return tenants.size() > 0
    }

    suspend fun createNewChannel(tenantMail: String?){
        if(!isThereChannelWithOwner(tenantMail)){
            val landlordMail = ownersDAO.getOwner(tenantMail.toString())?.mail
            if(landlordMail != null && landlordMail != ""){
                val participants = listOf(landlordMail, tenantMail.toString())
                val db = FirebaseFirestore.getInstance()
                val channelsRef = db.collection("channels")
                val newChannel = Channel("", participants, Date(), emptyList())
                channelsRef.add(newChannel)

                val channelID = runBlocking { getChannelID(tenantMail) }
                runBlocking { updateChannelID(channelID) }
                runBlocking { addNewMessage(channelID) }
            }
        }
    }

    suspend fun getLatestCreatedChannel() : Channel?{
        val rentsRef = db.collection("channels")
            .orderBy("dateCreated", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
        val documents = rentsRef.documents
        return documents[0].toObject(Channel::class.java)
    }

    private fun getChatPartner(channel: Channel, currentUserMail: String): String {
        val participants = runBlocking { participantsNameSurname(channel) }

        return if (channel.participants[0] == currentUserMail) {
            participants[1]
        } else {
            participants[0]
        }
    }

    suspend fun participantsNameSurname(channel: Channel): List<String> {
        val participants = channel.participants
        val list = ArrayList<String>()
        val tenantsDAO = TenantsDAO()
        val ownersDAO = OwnersDAO()
        for (participant in participants){
            Log.w("PARTICIPANT", participant)
            if(tenantsDAO.isUserTenant(participant)){
                val tenant = runBlocking {tenantsDAO.getTenant(participant)}
                Log.w("TENANT", "${tenant?.name} ${tenant?.surname}")
                list.add("${tenant?.name} ${tenant?.surname}")
            }else{
                val owner = runBlocking {ownersDAO.getOwnerInfo(participant)}
                Log.w("OWNER", "${owner?.name} ${owner?.surname}")
                list.add("${owner?.name} ${owner?.surname}")
            }
        }
        return list
    }
}