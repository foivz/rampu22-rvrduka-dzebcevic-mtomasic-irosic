package hr.foi.rampu.stanarko.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FlatsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getFlatByID(flatID : Int): Task<QuerySnapshot> {
        return db.collection("flats")
            .whereEqualTo("id", flatID)
            .get()
    }
    fun getAllFlats(): Task<QuerySnapshot> {
        return db.collection("flats").get()
    }
}