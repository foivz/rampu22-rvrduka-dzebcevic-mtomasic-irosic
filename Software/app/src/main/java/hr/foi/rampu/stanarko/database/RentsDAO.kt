package hr.foi.rampu.stanarko.database

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hr.foi.rampu.stanarko.entities.Rent
import hr.foi.rampu.stanarko.entities.Tenant
import java.text.SimpleDateFormat
import java.util.*

class RentsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun getAllRentsByTenantID(tenantID: Int): Task<QuerySnapshot> {
        return db.collection("rents")
            .whereEqualTo("tenant.id", tenantID)
            .get()
    }

    fun getAllRentsByTenantID(tenantID: Int, paid:Boolean): Task<QuerySnapshot> {
        return db.collection("rents")
            .whereEqualTo("tenant.id", tenantID)
            .whereEqualTo("rent_paid", paid)
            .get()
    }

    fun getAllRentsByOwnerID(ownerID: Int): Task<QuerySnapshot> {
        return db.collection("rents")
            .whereEqualTo("tenant.flat.owner.id", ownerID)
            .get()
    }

    fun getAllRentsByOwnerID(ownerID: Int, paid:Boolean): Task<QuerySnapshot> {
        return db.collection("rents")
            .whereEqualTo("tenant.flat.owner.id", ownerID)
            .whereEqualTo("rent_paid", paid)
            .get()
    }

    fun checkForRents() {
        val tenantsDAO = TenantsDAO()
        tenantsDAO.getTenantsWithFlat()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val tenant = document.toObject(Tenant::class.java)
                    checkForMissingRents(tenant)
                }
            }
    }

    private fun checkForMissingRents(tenant: Tenant) {
        val rentsRef = db.collection("rents")
        val dateFormat = SimpleDateFormat("MM/yyyy", Locale.getDefault())
        val currentDate = Date()
        val currentMonthAndYear = dateFormat.format(currentDate).split("/")

        val currentMonth = currentMonthAndYear[0].toInt()
        val currentYear = currentMonthAndYear[1].toInt()

        val userDate: String = tenant.dateOfMovingIn
        val userMonth: Int = userDate.substring(5, 7).toInt()
        val userYear: Int = userDate.substring(0, 4).toInt()

        val startMonth = userMonth
        val startYear = userYear
        val endMonth = currentMonth
        val endYear = currentYear

        for (year in startYear..endYear) {
            val monthStart = if (year == startYear) startMonth else 1
            val monthEnd = if (year == endYear) endMonth else 12

            for (month in monthStart..monthEnd) {
                rentsRef
                    .whereEqualTo("tenant.mail", tenant.mail)
                    .whereEqualTo("month_to_be_paid", month)
                    .whereEqualTo("year_to_be_paid", year)
                    .get()
                    .addOnSuccessListener { rents ->
                        if (rents.isEmpty) {
                            createRent(tenant, month, year)
                        }
                    }
            }
        }
    }

    private fun createRent(tenant: Tenant, month: Int, year: Int) {
        val rentsRef = db.collection("rents")
        rentsRef.get()
            .addOnSuccessListener { snapshot ->
                val sizeOfCollection = snapshot.size()
                rentsRef.add(Rent(sizeOfCollection + 1, tenant, month, year, false))
            }
    }

    fun payRentByDocumentID(attribute: String, value: Any) {
        val rentsRef = db.collection("rents").whereEqualTo(attribute, value)
        rentsRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                logError("Listen failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val allRents = arrayListOf<Rent>()
                val documents = snapshot.documents
                documents.forEach {
                    val rent = it.toObject(Rent::class.java)
                    if (rent != null) {
                        updateRentPaidStatus(it.id)
                    }
                }
            }
        }
    }

    private fun updateRentPaidStatus(documentId: String) {
        val documentReference = db.collection("rents").document(documentId)
        documentReference.update("rent_paid", true)
            .addOnSuccessListener {
                Log.w("BILL PAID", "PAID")
            }
            .addOnFailureListener { e ->
                logError("BILL NOT PAID", e)
            }
    }

    private fun logError(message: String, error: Exception) {
        Log.w(message, error)
    }
}