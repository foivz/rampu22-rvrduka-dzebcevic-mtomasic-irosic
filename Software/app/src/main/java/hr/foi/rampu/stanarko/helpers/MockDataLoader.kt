package hr.foi.rampu.stanarko.helpers

import hr.foi.rampu.stanarko.entities.Flat
import hr.foi.rampu.stanarko.entities.Owner
import hr.foi.rampu.stanarko.entities.Tenant

object MockDataLoader {
    fun getDemoFlat(): List<Flat> = listOf(
        Flat(1,"Petra Kresimira IV 42","Varazdin", Owner(1,"Pero","Peric",998887766,"peroPeric@gmail.com", "123"),false,44250 ),
        Flat(2,"Ivice Kresimira IV 42","Zagreb", Owner(1,"Pero","Peric",998887766,"peroPeric@gmail.com", "123"),true,42000 ),
        Flat(3,"Marice Kresimira IV 42","Varazdin", Owner(1,"Pero","Peric",998887766,"peroPeric@gmail.com", "123"),true,44350 ),
        Flat(4,"Petra Kresimira IV 42","Rijeka", Owner(1,"Pero","Peric",998887766,"peroPeric@gmail.com", "123"),false,44250 ),
        Flat(5,"Petra Kresimira IV 42","Sisak", Owner(1,"Marko","Markic",998887456,"markoMarkic@gmail.com", "123"),true,42000 ),
        Flat(6,"Martina Kresimira IV 42","Karlovac", Owner(1,"Marko","Markic",998887456,"markoMarkic@gmail.com", "123"),false,42000 )
        )
    fun getDemoTenant(): List<Tenant> = listOf(
        Tenant(1,"Ivan","Ivanic",98764854,"ivanIvanic@gmail.com","123", getDemoFlat()[0]),
        Tenant(2,"Ivana","Ivanic",987421854,"ivanaIvanic@gmail.com","123", getDemoFlat()[0]),
        Tenant(3,"Marko","Markic",98742354,"markoMarkic@gmail.com","123", getDemoFlat()[1]),
        Tenant(4,"Ljubo","Zlatan",98214854,"ljuboZlatan@gmail.com","123", getDemoFlat()[4])
    )
}