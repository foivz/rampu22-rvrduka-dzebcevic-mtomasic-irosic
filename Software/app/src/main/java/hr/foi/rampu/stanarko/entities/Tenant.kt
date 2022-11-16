package hr.foi.rampu.stanarko.entities

class Tenant(
    id: Int,
    name: String,
    surname: String,
    phoneNumber: Int,
    mail: String,
    role: Role = Role.TENANT,
    flat: Flat
) : Person(id, name, surname, phoneNumber, mail, role)

