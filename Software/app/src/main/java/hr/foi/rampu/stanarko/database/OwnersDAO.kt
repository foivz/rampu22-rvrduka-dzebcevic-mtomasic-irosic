package hr.foi.rampu.stanarko.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hr.foi.rampu.stanarko.entities.Flat

class OwnersDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getOwnerByEmail(ownerMail : String): Task<QuerySnapshot> {
        return db.collection("owners")
            .whereEqualTo("mail", ownerMail)
            .get()
    }

}