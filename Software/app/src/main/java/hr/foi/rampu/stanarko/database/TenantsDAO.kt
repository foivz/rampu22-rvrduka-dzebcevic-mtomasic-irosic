package hr.foi.rampu.stanarko.database

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.model.mutation.Precondition.exists
import hr.foi.rampu.stanarko.entities.Tenant
import kotlinx.coroutines.tasks.await

class TenantsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun isUserTenant(userMail: String) : Boolean{
        var tenants = db.collection("tenants")
            .whereEqualTo("mail", userMail)
            .get().await()
        return tenants.size() > 0
    }

    fun getTenantByID(tenantID : Int): Task<QuerySnapshot> {
        return db.collection("flats")
            .whereEqualTo("id", tenantID)
            .get()
    }

    fun getAllTenants(): Task<QuerySnapshot> {
        return db.collection("tenants").get()
    }

    fun getTenantsWithFlat(): Task<QuerySnapshot> {
        return db.collection("tenants")
            .whereNotEqualTo("flat", null)
            .get()
    }

    fun createTenant(tenant: Tenant, context: Context){
        db.collection("tenants").add(tenant).addOnFailureListener { e ->
            Toast.makeText(context,"Error:${e.message}",Toast.LENGTH_SHORT).show()
        }
    }
}