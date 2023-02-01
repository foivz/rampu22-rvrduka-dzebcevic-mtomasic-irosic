package hr.foi.rampu.stanarko.helpers

import android.util.Log
import hr.foi.rampu.stanarko.database.FlatsDAO
import hr.foi.rampu.stanarko.database.MalfunctionsDAO
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.entities.*
import kotlinx.coroutines.tasks.await

object MockDataLoader {
    suspend fun getFirebaseFlats(): MutableList<Flat> {
        val flatsDAO = FlatsDAO()
        val flats = mutableListOf<Flat>()
        val result = flatsDAO.getAllFlats().await()
        flats.addAll(result.toObjects(Flat::class.java))
        return flats
    }

    fun testMAL(): MutableList<Malfunction> {
        val malfuncDAO = MalfunctionsDAO()
        val mal = mutableListOf<Malfunction>()
        malfuncDAO.getAllMalfunctions().addOnSuccessListener { querySnapshot ->
            mal.addAll(querySnapshot.toObjects(Malfunction::class.java))
        }
        return mal
    }
    suspend fun getFirebaseMalfunctions(): MutableList<Malfunction> {
        val malfunctionDAO = MalfunctionsDAO()
        val flats = mutableListOf<Malfunction>()
        val result = malfunctionDAO.getAllMalfunctions().await()
        flats.addAll(result.toObjects(Malfunction::class.java))
        return flats

    }
    suspend fun getFirebaseFlatsByOwner(mail: String): MutableList<Flat> {
        val flatsDAO = FlatsDAO()
        val flats = mutableListOf<Flat>()
        val result = flatsDAO.getFlatsByOwnerMail(mail).await()
        flats.addAll(result.toObjects(Flat::class.java))
        return flats

    }

    suspend fun getFirebaseTenants(id: Int): List<Tenant> {
        val tenantsDAO = TenantsDAO()
        val tenants = mutableListOf<Tenant>()
        val result = tenantsDAO.getTenantsByFlatId(id).await()
        tenants.addAll(result.toObjects(Tenant::class.java))
        return tenants
    }

    suspend fun getTenantByMail(userMail : String) : Tenant {
        val tenantsDAO = TenantsDAO()
        return tenantsDAO.getTenant(userMail)!!
    }

    suspend fun getFirebaseTenantsByAdress(adresa: String): List<Tenant> {
        val tenantsDAO = TenantsDAO()
        val tenants = mutableListOf<Tenant>()
        val result = tenantsDAO.getTenantsByFlatAddress(adresa).await()
        tenants.addAll(result.toObjects(Tenant::class.java))
        return tenants
    }
}