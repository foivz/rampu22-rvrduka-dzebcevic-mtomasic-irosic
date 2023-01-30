package hr.foi.rampu.stanarko.entities

data class Flat(
    val id : Int,
    val address: String,
    val city: String,
    val owner: Owner?,
    val occupied: Boolean,
    val amount: Double,
    val postalCode: Int,
    val tenants: List<Tenant> = emptyList()

){
    constructor() : this(0, "", "", null, false,0.00,0, emptyList())
}
