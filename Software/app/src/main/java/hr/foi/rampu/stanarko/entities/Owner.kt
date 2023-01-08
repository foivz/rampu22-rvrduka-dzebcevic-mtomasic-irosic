package hr.foi.rampu.stanarko.entities

class Owner(
    name: String,
    surname: String,
    phoneNumber: String,
    mail: String,
    role: Role = Role.OWNER
) : Person(name, surname, phoneNumber, mail, role){

    constructor() : this("", "", "", "", Role.OWNER)
}

