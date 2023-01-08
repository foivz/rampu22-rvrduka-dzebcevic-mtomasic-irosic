package hr.foi.rampu.stanarko.database

import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hr.foi.rampu.stanarko.entities.Tenant

class TenantsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getTenantByID(tenantID : Int): Task<QuerySnapshot> {
        return db.collection("flats")
            .whereEqualTo("id", tenantID)
            .get()
    }

    fun getAllTenants(): Task<QuerySnapshot> {
        return db.collection("tenants").get()
    }

    fun createTenant(tenant: Tenant, context: Context){
        db.collection("tenants").add(tenant).addOnSuccessListener {
            Toast.makeText(context, "Data inserted",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { e ->
            Toast.makeText(context,"Error:${e.message}",Toast.LENGTH_SHORT).show()
        }
    }
}