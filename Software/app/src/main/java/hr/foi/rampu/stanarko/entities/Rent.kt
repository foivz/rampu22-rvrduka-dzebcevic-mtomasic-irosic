package hr.foi.rampu.stanarko.entities

class Rent(
    id: Int,
    tenant: Tenant,
    month_to_be_paid: Int,
    year_to_be_paid: Int,
    amount: Double,
    rent_paid: Boolean = false
)