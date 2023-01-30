package hr.foi.rampu.stanarko.database

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.common.util.concurrent.ListenableFutureTask
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.model.mutation.Precondition.exists
import hr.foi.rampu.stanarko.entities.Tenant
import kotlinx.coroutines.tasks.await

class TenantsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val tenantsRef = db.collection("tenants")

    suspend fun isUserTenant(userMail: String) : Boolean{
        var tenant = db.collection("tenants")
            .whereEqualTo("mail", userMail)
            .get()
            .await()
        return tenant.size() > 0
    }

    suspend fun isUserInFlat(userMail: String) : Boolean{
        var tenant = db.collection("tenants")
            .whereEqualTo("mail", userMail)
            .whereEqualTo("flat", null)
            .get()
            .await()
        return tenant.size() <= 0
    }

    suspend fun getTenant(userMail: String) : Tenant? {
        var tenant = db.collection("tenants")
            .whereEqualTo("mail", userMail)
            .get()
            .await()
        return tenant.documents[0].toObject(Tenant::class.java)
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

    suspend fun getUncontactedTenants(currentUserMail: String): MutableList<Tenant> {
        val tenants = tenantsRef
            .whereEqualTo("flat.owner.mail", currentUserMail)
            .get()
            .await()
            .toObjects(Tenant::class.java)
        return tenants
    }

    fun createTenant(tenant: Tenant, context: Context){
        db.collection("tenants").add(tenant).addOnFailureListener { e ->
            Toast.makeText(context,"Error:${e.message}",Toast.LENGTH_SHORT).show()
        }
    }
}