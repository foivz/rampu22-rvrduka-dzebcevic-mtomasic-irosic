package hr.foi.rampu.stanarko.database

import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.stanarko.entities.Rent
import hr.foi.rampu.stanarko.entities.Tenant
import kotlinx.coroutines.tasks.await

class OwnersDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun getLandlord(tenantMail: String) : String{
        val rentsRef = db.collection("tenants")
            .whereEqualTo("mail", tenantMail)
            .get().await()
        val documents = rentsRef.documents
        val tenant = documents[0].toObject(Tenant::class.java)
        return tenant?.flat?.owner?.mail.toString()
    }
}