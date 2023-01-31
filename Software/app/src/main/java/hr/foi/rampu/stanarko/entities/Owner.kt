package hr.foi.rampu.stanarko.entities

class Owner(
    name: String,
    surname: String,
    phoneNumber: String,
    mail: String,
    password: String,
    role: Role = Role.OWNER,
    token: String
) : Person( name, surname, phoneNumber, mail, password,role){

    constructor() : this( "", "", "", "", "", Role.OWNER, "")
}