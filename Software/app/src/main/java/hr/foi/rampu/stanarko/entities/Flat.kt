package hr.foi.rampu.stanarko.entities

data class Flat(
    val id : Int,
    val address: String,
    val city: String,
    val owner: User,
    val occupied: Boolean,
    val postal_code: Int
)
