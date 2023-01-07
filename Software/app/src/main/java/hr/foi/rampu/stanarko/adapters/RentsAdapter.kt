package hr.foi.rampu.stanarko.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.entities.Rent
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class RentsAdapter(private val rentLists: List<Rent>) : RecyclerView.Adapter<RentsAdapter.RentViewHolder>() {
    inner class RentViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        private val sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.ENGLISH)

        private val rentPersonName : TextView
        private val rentPersonAddress : TextView
        private val rentPersonDateOfMovingIn : TextView
        private val rentDueMonth : TextView
        private val rentDueYear : TextView
        private val rentDueMonthYear : LinearLayout
        private val rentAmount : TextView
        init {
            rentPersonName = view.findViewById(R.id.tv_rent_person_name)
            rentPersonAddress = view.findViewById(R.id.tv_rent_person_address)
            rentPersonDateOfMovingIn = view.findViewById(R.id.tv_rent_date_of_moving_in)
            rentDueMonth = view.findViewById(R.id.tv_rent_due_month)
            rentDueYear = view.findViewById(R.id.tv_rent_due_year)
            rentDueMonthYear = view.findViewById(R.id.ll_month_year)
            rentAmount = view.findViewById(R.id.tv_rent_amount)
        }

        @SuppressLint("SetTextI18n")
        fun bind(rent: Rent) {
            rentPersonName.text = "${rent.tenant.name} ${rent.tenant.surname}";
            //rentPersonAddress.text = rent.tenant.flat
            //rentPersonDateOfMovingIn.text = "since. ${rent.tenant.dateOfMovingIn}"
            //rentDueMonth.text = rent.dueMonth.toString()
            rentDueMonth.text = formatMonth(rent.month_to_be_paid).substring(0,3).uppercase()
            rentDueYear.text = rent.year_to_be_paid.toString()
            //setDueMonthYearColor(rent.paid)
            //rentAmount.text = "${rent.person.apartment?.price.toString()} €"
        }

        private fun formatMonth(month: Int): String {
            val symbols = DateFormatSymbols(Locale.ENGLISH)
            val monthNames: Array<String> = symbols.months
            return monthNames[month - 1]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentViewHolder {
        val rentView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rent_list_item, parent, false)
        return RentViewHolder(rentView)
    }

    override fun onBindViewHolder(holder: RentViewHolder, position: Int) {
        holder.bind(rentLists[position])
    }

    override fun getItemCount() = rentLists.size
}