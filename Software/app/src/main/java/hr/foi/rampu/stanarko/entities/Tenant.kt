package hr.foi.rampu.stanarko.entities

class Tenant(
    id: Int,
    name: String,
    surname: String,
    phoneNumber: String,
    mail: String,
    password: String,
    val flat: Flat?,
    val dateOfMovingIn: String,
    role: Role = Role.TENANT
) : Person(id, name, surname, phoneNumber, mail, password, role){
    constructor() : this(0, "", "", "", "", "", null, "", Role.TENANT)
}