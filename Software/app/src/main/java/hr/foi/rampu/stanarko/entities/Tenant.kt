package hr.foi.rampu.stanarko.entities

class Tenant(
    id: Int,
    name: String,
    surname: String,
    phoneNumber: Int,
    mail: String,
    flat: Flat,
    role: Role = Role.TENANT
) : Person(id, name, surname, phoneNumber, mail, role)

