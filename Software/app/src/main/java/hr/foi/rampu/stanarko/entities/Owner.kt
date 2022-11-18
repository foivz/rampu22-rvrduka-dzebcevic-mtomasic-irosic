package hr.foi.rampu.stanarko.entities

class Owner(
    id: Int,
    name: String,
    surname: String,
    phoneNumber: Int,
    mail: String,
    password: String,
    role: Role = Role.OWNER
) : Person(id, name, surname, phoneNumber, mail, password,role)
