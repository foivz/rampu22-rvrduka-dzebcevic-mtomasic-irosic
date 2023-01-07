package hr.foi.rampu.stanarko.entities

class Tenant(
    id: Int,
    name: String,
    surname: String,
    phoneNumber: String,
    mail: String,
    val flat: Flat?,
    val dateOfMovingIn: String,
    role: Role = Role.TENANT
) : Person(id, name, surname, phoneNumber, mail, role){

    constructor() : this(0, "", "", "", "", null, "", Role.TENANT)
}


