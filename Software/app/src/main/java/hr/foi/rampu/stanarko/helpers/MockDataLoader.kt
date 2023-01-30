package hr.foi.rampu.stanarko.helpers

import hr.foi.rampu.stanarko.database.FlatsDAO
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.entities.Flat
import hr.foi.rampu.stanarko.entities.Owner
import hr.foi.rampu.stanarko.entities.Rent
import hr.foi.rampu.stanarko.entities.Tenant
import kotlinx.coroutines.tasks.await

object MockDataLoader {
    fun getDemoFlat(): List<Flat> = listOf(
        Flat(1,"Petra Kresimira IV 42","Varazdin", Owner("Pero","Peric","998887766","peroPeric@gmail.com","123"),false,400.00,44250 ),
        Flat(2,"Ivice Kresimira IV 42","Zagreb", Owner("Pero","Peric","998887766","peroPeric@gmail.com","123"),true,350.00, 42000 ),
        Flat(3,"Marice Kresimira IV 42","Varazdin", Owner("Pero","Peric","998887766","peroPeric@gmail.com","123"),true,450.00, 44350 ),
        Flat(4,"Petra Kresimira IV 42","Rijeka", Owner("Pero","Peric","998887766","peroPeric@gmail.com","123"),false,400.00, 44250 ),
        Flat(5,"Petra Kresimira IV 42","Sisak", Owner("Marko","Markic","998887456","markoMarkic@gmail.com","123"),true,450.00, 42000 ),
        Flat(6,"Martina Kresimira IV 42","Karlovac", Owner("Marko","Markic","998887456","markoMarkic@gmail.com","123"),false,350.00, 42000 )
    )
    suspend fun getFirebaseFlats(): List<Flat> {
        val flatsDAO = FlatsDAO()
        val flats = mutableListOf<Flat>()
        val result = flatsDAO.getAllFlats().await()
        flats.addAll(result.toObjects(Flat::class.java))
        return flats
    }
    suspend fun getFirebaseTenants(id: Int): List<Tenant> {
       // val tenantsDAO = TenantsDAO()
       // val tenants = mutableListOf<Tenant>()
       // val result = tenantsDAO.getTenantsByFlatId(id).await()
      //  tenants.addAll(result.toObjects(Tenant::class.java))
        return emptyList()
    }

    fun getDemoTenant(): List<Tenant> = listOf(
        Tenant("Ivan","Ivanic","98764854","ivanIvanic@gmail.com","123", getDemoFlat()[0], "01-01-2023"),
        Tenant("Ivana","Ivanic","987421854","ivanaIvanic@gmail.com","123", getDemoFlat()[0], "01-01-2023"),
        Tenant("Marko","Markic","98742354","markoMarkic@gmail.com","123", getDemoFlat()[1], "01-01-2023"),
        Tenant("Ljubo","Zlatan","98214854","ljuboZlatan@gmail.com","123", getDemoFlat()[4], "01-01-2023")
    )
    fun getDemoRent(): List<Rent> = listOf(
        Rent(1,getDemoTenant()[0], 11,2022,false),
        Rent(2,getDemoTenant()[1], 11,2022,false),
        Rent(3,getDemoTenant()[2], 11,2022,false)
    )
}