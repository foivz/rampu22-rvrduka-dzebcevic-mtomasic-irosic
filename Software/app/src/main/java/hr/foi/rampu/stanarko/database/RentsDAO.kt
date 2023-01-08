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

    fun checkForRents(){
        val dateFormat = SimpleDateFormat("MM/yyyy", Locale.getDefault())
        val currentDate = Date()
        val currentMonthAndYear = dateFormat.format(currentDate).split("/")

        val currentMonth = currentMonthAndYear[0].toInt()
        val currentYear = currentMonthAndYear[1].toInt()

        val tenantsDAO = TenantsDAO()
        val rentsRef = db.collection("rents")
        var sizeOfColletion = 0

        var rentsToBeWritten = mutableListOf<Rent>()

        rentsRef.get()
            .addOnSuccessListener { snapshot ->
                sizeOfColletion = snapshot.size()
            }

        tenantsDAO.getTenantsWithFlat()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val tenant = document.toObject(Tenant::class.java)
                    val userDate: String = tenant.dateOfMovingIn
                    var userMonth: Int = userDate.substring(5, 7).toInt()
                    var userYear: Int = userDate.substring(0, 4).toInt()

                    Log.w("PERSON", tenant.id.toString() + " " + tenant.name + " " + tenant.surname + " " + userMonth.toString() + " " + userYear.toString())

                    val startMonth = userMonth
                    val startYear = userYear
                    var endMonth = currentMonth
                    var endYear = currentYear


                    for (year in startYear..endYear) {
                        val monthStart = if (year == startYear) startMonth else 1
                        val monthEnd = if (year == endYear) endMonth else 12

                        for (month in monthStart..monthEnd) {
                            rentsRef
                                .whereEqualTo("tenant.id", tenant.id)
                                .whereEqualTo("month_to_be_paid", month)
                                .whereEqualTo("year_to_be_paid", year)
                                .get()
                                .addOnSuccessListener { rents ->
                                    if (rents.isEmpty) {
                                        //Log.w("RENT MISSING", tenant.id.toString() + " Nema za ovaj: " + month.toString() + " " + year.toString())
                                        rentsRef.add(Rent(sizeOfColletion + 1, tenant, month, year, false))
                                    }
                                }
                        }

                    }

                }

            }
    }

    fun payRentByDocumentID(attribute: String, value: Any){
        db.collection("rents").whereEqualTo(attribute, value).addSnapshotListener {
                snapshot, e ->
            if(e != null){
                Log.w("Check for error", "Listen failed", e)
                return@addSnapshotListener
            }
            if(snapshot != null){
                val allRents = arrayListOf<Rent>()
                val documents = snapshot.documents
                documents.forEach{
                    val rent = it.toObject(Rent::class.java)
                    if(rent != null){
                        Log.w("DOCUMENT ID", it.id);
                        val documentReference = db.collection("rents").document(it.id)
                        documentReference.update("rent_paid", true)
                            .addOnSuccessListener {
                                Log.w("RACUN JE PLACEN", "DADADAD");
                            }
                            .addOnFailureListener { e ->
                                Log.w("RACUN NIJEJE PLACEN", "DADADAD");
                            }
                    }
                }
            }
        }
    }
}