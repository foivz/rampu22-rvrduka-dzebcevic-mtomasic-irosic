package hr.foi.rampu.stanarko.database

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
        val rentsRef = db.collection("channels")
            .whereArrayContains("participants", mail.toString())
            .get()
            .await()
        val documents = rentsRef.documents
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

    suspend fun participantsNameSurname(channel: Channel): List<String> {
        val participants = channel.participants
        val list = ArrayList<String>()
        val tenantsDAO = TenantsDAO()
        val ownersDAO = OwnersDAO()
        for (participant in participants){
            if(tenantsDAO.isUserTenant(participant)){
                val participant = runBlocking {tenantsDAO.getTenant(participant)}
                list.add("${participant?.name} ${participant?.surname}")
            }else{
                val owner = runBlocking {ownersDAO.getOwnerInfo(participant)}
                list.add("${owner?.name} ${owner?.surname}")
            }
        }
        return list
    }
}