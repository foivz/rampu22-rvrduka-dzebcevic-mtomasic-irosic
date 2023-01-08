package hr.foi.rampu.stanarko.entities

class Tenant(
    name: String,
    surname: String,
    phoneNumber: String,
    mail: String,
    val flat: Flat?,
    val dateOfMovingIn: String,
    role: Role = Role.TENANT
) : Person(name, surname, phoneNumber, mail, role){

    constructor() : this("", "", "", "", null, "", Role.TENANT)
}


