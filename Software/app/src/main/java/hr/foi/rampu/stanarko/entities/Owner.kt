package hr.foi.rampu.stanarko.entities

class Owner(
    id: Int,
    name: String,
    surname: String,
    phoneNumber: String,
    mail: String,
    password: String,
    role: Role = Role.OWNER
) : Person(id, name, surname, phoneNumber, mail, password,role){

    constructor() : this(0, "", "", "", "", "", Role.OWNER)
}