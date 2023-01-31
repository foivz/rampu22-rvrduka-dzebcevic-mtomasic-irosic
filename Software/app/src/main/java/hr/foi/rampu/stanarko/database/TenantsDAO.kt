package hr.foi.rampu.stanarko.database

import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hr.foi.rampu.stanarko.entities.Tenant
import kotlinx.coroutines.tasks.await

class TenantsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val tenantsRef = db.collection("tenants")

    suspend fun isUserTenant(userMail: String) : Boolean{
        val tenant = tenantsRef
            .whereEqualTo("mail", userMail)
            .get()
            .await()
        return tenant.size() > 0
    }

    suspend fun isUserInFlat(userMail: String) : Boolean{
        val tenant = tenantsRef
            .whereEqualTo("mail", userMail)
            .whereEqualTo("flat", null)
            .get()
            .await()
        return tenant.size() <= 0
    }

    suspend fun getTenant(userMail: String) : Tenant? {
        val tenant = tenantsRef
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
        return tenantsRef
            .get()
    }

    fun getTenantsWithFlat(): Task<QuerySnapshot> {
        return tenantsRef
            .whereNotEqualTo("flat", null)
            .get()
    }

    suspend fun getUncontactedTenants(currentUserMail: String): MutableList<Tenant> {
        val tenants = tenantsRef
            .whereEqualTo("flat.owner.mail", currentUserMail)
            .get()
            .await()
            .toObjects(Tenant::class.java)

        val filteredTenants = ArrayList<Tenant>()
        for (tenant in tenants){
            val result = db.collection("channels").whereArrayContains("participants", tenant.mail).get().await()
            if(result.size()<=0){
                filteredTenants.add(tenant)
            }
        }
        return filteredTenants
    }

    fun createTenant(tenant: Tenant, context: Context){
        tenantsRef.add(tenant).addOnFailureListener { e ->
            Toast.makeText(context,"Error:${e.message}",Toast.LENGTH_SHORT).show()
        }
    }

    fun getTenantsByFlatId(flatID : Int): Task<QuerySnapshot> {
        return tenantsRef
            .whereEqualTo("flat.id", flatID)
            .get()
    }
    fun getTenantsByFlatAddress(flatAddress : String): Task<QuerySnapshot> {
        return tenantsRef
            .whereEqualTo("flat.address", flatAddress)
            .get()
    }
}