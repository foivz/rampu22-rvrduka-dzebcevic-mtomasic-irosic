package hr.foi.rampu.stanarko.database

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import hr.foi.rampu.stanarko.entities.Rent
import hr.foi.rampu.stanarko.entities.Tenant
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class RentsDAO {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun getAllRents(): Task<QuerySnapshot> {
        return db.collection("rents").get()
    }

    fun getAllRents(paid:Boolean): Task<QuerySnapshot> {
        return db.collection("rents")
            .whereEqualTo("rent_paid", paid)
            .get()
    }

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

    fun getAllRentsByMail(mail: String, paid:Boolean): Task<QuerySnapshot> {
        return db.collection("rents")
            .whereEqualTo("tenant.mail", mail)
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

    fun getAllRentByIDDueMonthYear(addedRentId: Int, addedRentMonthDue: Int, addedRentYearDue: Int): Query {
        return db.collection("rents")
            .whereEqualTo("id", addedRentId)
            .whereEqualTo("month_to_be_paid", addedRentMonthDue)
            .whereEqualTo("year_to_be_paid", addedRentYearDue)
    }

    fun getAllRentByIDDueMonthYearTest(mailRent: String, addedRentMonthDue: Int, addedRentYearDue: Int): Task<QuerySnapshot> {
        return db.collection("rents")
            .whereEqualTo("tenant.mail", mailRent)
            .whereEqualTo("month_to_be_paid", addedRentMonthDue)
            .whereEqualTo("year_to_be_paid", addedRentYearDue)
            .get()
    }

    fun checkForRents() {
        val tenantsDAO = TenantsDAO()
        tenantsDAO.getTenantsWithFlat()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val tenant = document.toObject(Tenant::class.java)
                    runBlocking { checkForMissingRents(tenant) }
                }
            }
    }

    private suspend fun checkForMissingRents(tenant: Tenant) {
        val dateFormat = SimpleDateFormat("MM/yyyy", Locale.getDefault())
        val currentDate = Date()
        val currentMonthAndYear = dateFormat.format(currentDate).split("/")

        val currentMonth = currentMonthAndYear[0].toInt()
        val currentYear = currentMonthAndYear[1].toInt()

        val userDate: String? = tenant.dateOfMovingIn

        val startMonth : Int = userDate?.substring(5, 7)!!.toInt()
        val startYear : Int = userDate?.substring(0, 4)!!.toInt()

        for (year in startYear..currentYear) {
            val monthStart = if (year == startYear) startMonth else 1
            val monthEnd = if (year == currentYear) currentMonth else 12

            for (month in monthStart..monthEnd) {

                val size = getAllRentByIDDueMonthYearTest(tenant.mail, month, year).await().size()
                if (size == 0) {
                    runBlocking { createRent(tenant, month, year) }
                } else {
                    Log.w("ERRRRRRRRRR", "ERRRRRRRRRR")
                }
            }
        }
    }

    private suspend fun createRent(tenant: Tenant, month: Int, year: Int) {
        val rents = mutableListOf<Rent>()
        val result = getAllRents().await()
        rents.addAll(result.toObjects(Rent::class.java))

        val rentsSize = rents.size
        val rentsRef = db.collection("rents")

        rentsRef.add(Rent(rentsSize + 1, tenant, month, year, false))
    }

    suspend fun payRentByDocumentID(value: Any, value2: Any, value3: Any) {
        val rentsRef = db.collection("rents").whereEqualTo("id", value).whereEqualTo("month_to_be_paid", value2).whereEqualTo("year_to_be_paid", value3)
        rentsRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("Listen failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val documents = snapshot.documents
                documents.forEach {
                    val rent = it.toObject(Rent::class.java)
                    if (rent != null) {
                        runBlocking {updateRentPaidStatus(it.id)}
                    }
                }
            }
        }
    }

    private suspend fun updateRentPaidStatus(documentId: String) {
        val documentReference = db.collection("rents").document(documentId)
        documentReference.update("rent_paid", true).await()
    }
}

