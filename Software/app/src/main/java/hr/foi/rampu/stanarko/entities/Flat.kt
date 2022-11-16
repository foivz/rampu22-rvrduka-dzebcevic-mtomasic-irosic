package hr.foi.rampu.stanarko.entities

data class Flat(
    val id : Int,
    val address: String,
    val city: String,
    val owner: Owner,
    val occupied: Boolean,
    val postalCode: Int
)
