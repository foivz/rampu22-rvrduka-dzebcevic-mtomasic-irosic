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

        //val currentMonth = currentMonthAndYear[0].toInt()

        val currentMonth = 3;
        val currentYear = currentMonthAndYear[1].toInt()

        Log.w("TEST", "$currentMonth. mjesec $currentYear. godina");

        val tenantsDAO = TenantsDAO()
        val rentsRef = db.collection("rents")

        tenantsDAO.getAllTenants()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val tenant = document.toObject(Tenant::class.java)
                    val userDate: String = tenant.dateOfMovingIn
                    var userMonth: Int = userDate.substring(5, 7).toInt()
                    var userYear: Int = userDate.substring(0, 4).toInt()
                    Log.w("PERSON", tenant.id.toString() + " " + tenant.name + " " + tenant.surname + " " + userMonth.toString() + " " + userYear.toString())


                    while (userYear < currentYear || (userYear == currentYear && userMonth <= currentMonth)) {
                        // your code here
                        println("Month: $userMonth, Year: $userYear")

                        if (userMonth == 12) {
                            userMonth = 1
                            userYear += 1
                        } else {
                            userMonth += 1
                        }
                    }
                    /*
                    for (i in userYear..currentYear) {
                        while (userMonth != currentMonth + 1) {
                            rentsRef
                                .whereEqualTo("id", tenant.id)
                                .whereEqualTo("month_to_be_paid", userMonth)
                                .whereEqualTo("year_to_be_paid", i)
                                .get()
                                .addOnSuccessListener { rents ->
                                    if (rents.isEmpty) {
                                        Log.w("PERSON", tenant.id.toString() + " Nema za ovaj: " + userMonth.toString() + " " + i.toString())
                                        // If there is no such rent bill, create a new one and add it to the list
                                        //val newRent = Rent(newRentList.size + 1, user, userMonth, i, false)
                                        //newRentList.add(newRent)
                                    }
                                }


                            if (userMonth == 12) {
                                userMonth = 1
                                userYear += 1
                            } else {
                                userMonth += 1
                            }
                        }
                    }
                    */
                }
            }
    }
}