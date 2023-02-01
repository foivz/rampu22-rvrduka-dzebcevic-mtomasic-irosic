package hr.foi.rampu.stanarko.database

import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hr.foi.rampu.stanarko.entities.Malfunction

class MalfunctionsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun addMalfunction(malfunction: Malfunction, context: Context){
        db.collection("malfunctions").add(malfunction).addOnFailureListener { e ->
            Toast.makeText(context,"Error:${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    fun getAllMalfunctions(): Task<QuerySnapshot> {
        return db.collection("malfunctions").get()
    }
}