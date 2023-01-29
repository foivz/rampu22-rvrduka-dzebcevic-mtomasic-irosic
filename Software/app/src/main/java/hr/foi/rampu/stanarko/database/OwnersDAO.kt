package hr.foi.rampu.stanarko.database

import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.stanarko.entities.Owner
import hr.foi.rampu.stanarko.entities.Tenant
import kotlinx.coroutines.tasks.await

class OwnersDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun getOwner(tenantMail: String) : Owner?{
        val ownerRef = db.collection("tenants")
            .whereEqualTo("mail", tenantMail)
            .get()
            .await()
        val documents = ownerRef.documents
        val result = documents[0].toObject(Tenant::class.java)
        return result?.flat?.owner
    }
    suspend fun getOwnerInfo(ownerMail: String): Owner? {
        val ownerRef = db.collection("owners")
            .whereEqualTo("mail", ownerMail)
            .get()
            .await()
        val documents = ownerRef.documents
        return documents[0].toObject(Owner::class.java)
    }
}