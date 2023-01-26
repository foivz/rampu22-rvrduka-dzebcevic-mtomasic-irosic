package hr.foi.rampu.stanarko.database

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hr.foi.rampu.stanarko.entities.Rent
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class ChannelsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun getChannelID(mail: String?) : String {
        var channelID = "";
        val rentsRef = db.collection("channels").whereArrayContains("participants", mail.toString()).get().await()
        val documents = rentsRef.documents
        return documents[0].id
    }
}