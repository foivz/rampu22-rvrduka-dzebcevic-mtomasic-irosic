package hr.foi.rampu.stanarko.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class TenantsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getAllTenants(): Task<QuerySnapshot> {
        return db.collection("tenants").get()
    }
}