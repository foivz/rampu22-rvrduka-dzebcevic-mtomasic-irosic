package hr.foi.rampu.stanarko.entities


import java.util.*

data class Malfunction (
    var description: String,
    var flat: Flat?,
    var date: Date
){
    constructor(): this("", null, Date())
}