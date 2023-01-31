package hr.foi.rampu.stanarko.entities

class Tenant(
    name: String,
    surname: String,
    phoneNumber: String,
    mail: String,
    password: String,
    val flat: Flat?,
    val dateOfMovingIn: String?,
    role: Role = Role.TENANT
) : Person( name, surname, phoneNumber, mail, password, role){
    constructor() : this( "", "", "", "", "", null, "", Role.TENANT)
    override fun toString(): String {
        return "$name $surname"
    }
}