package hr.foi.rampu.stanarko.entities

import java.util.Date

class Tenant(
    name: String,
    surname: String,
    phoneNumber: String,
    mail: String,
    password: String,
    val flat: Flat?,
    val dateOfMovingIn: String?,
    val dateOfMovingOut: Date?,
    role: Role = Role.TENANT
) : Person( name, surname, phoneNumber, mail, password, role){
    constructor() : this( "", "", "", "", "", null, "",null, Role.TENANT)
}