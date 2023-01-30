package hr.foi.rampu.stanarko.database

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hr.foi.rampu.stanarko.entities.Flat
import hr.foi.rampu.stanarko.entities.Tenant

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

    fun removeFlat(attribute: String, value: Any, attribute2 : Int) {
        val db = FirebaseFirestore.getInstance()

        val referenceToDatabase = db.collection("flats").whereEqualTo(attribute, value)
        referenceToDatabase.addSnapshotListener{snapshot, e->
            if(e != null){
                Log.d("DADA", "GRESKA")
            }
            if(snapshot != null){
                val documents = snapshot.documents

                documents.forEach{
                    val helpVariable = it.toObject(Flat::class.java)
                    if(helpVariable != null){
                        var tenant = TenantsDAO()
                        tenant.getTenantsByFlatId(attribute2).addOnSuccessListener { snapshot ->
                            var allTenants = snapshot.toObjects(Tenant::class.java)
                            if(allTenants.isEmpty()){
                                db.collection("flats").document(it.id).delete()
                            }

                        }

                    }
                }
            }
        }
    }

}