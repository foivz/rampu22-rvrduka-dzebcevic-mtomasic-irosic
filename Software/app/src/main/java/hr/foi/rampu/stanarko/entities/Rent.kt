package hr.foi.rampu.stanarko.entities

data class Rent(
    val id: Int,
    val month_to_be_paid: Int,
    val year_to_be_paid: Int,
    val amount: Double,
    val rent_paid: Boolean
)