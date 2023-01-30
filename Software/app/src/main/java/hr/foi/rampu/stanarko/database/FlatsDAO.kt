package hr.foi.rampu.stanarko.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hr.foi.rampu.stanarko.entities.Flat

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
    fun AddFlat(referencedFlat: Flat){
        db.collection("flats").add(referencedFlat)
    }

}