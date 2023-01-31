package hr.foi.rampu.stanarko.helpers

import hr.foi.rampu.stanarko.database.FlatsDAO
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.entities.Flat
import hr.foi.rampu.stanarko.entities.Tenant
import kotlinx.coroutines.tasks.await

object DataLoader {

    suspend fun getFirebaseFlats(): List<Flat> {
        val flatsDAO = FlatsDAO()
        val flats = mutableListOf<Flat>()
        val result = flatsDAO.getAllFlats().await()
        flats.addAll(result.toObjects(Flat::class.java))
        return flats
    }
    suspend fun getFirebaseFlatsByOwner(mail: String): List<Flat> {
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
}