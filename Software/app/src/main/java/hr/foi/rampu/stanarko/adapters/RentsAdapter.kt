package hr.foi.rampu.stanarko.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.RentManagerActivity
import hr.foi.rampu.stanarko.database.RentsDAO
import hr.foi.rampu.stanarko.entities.Rent
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class RentsAdapter(private val rentLists: MutableList<Rent>,) : RecyclerView.Adapter<RentsAdapter.RentViewHolder>() {
    inner class RentViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        private val rentsDAO = RentsDAO();

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
            view.setOnClickListener {

                AlertDialog.Builder(view.context)
                    .setTitle(rentPersonName.text)
                    .setNeutralButton("Plati račun") { _, _ ->
                        val paidRent = rentLists[adapterPosition]
                        rentsDAO.payRentByDocumentID("id", paidRent.id)
                        removeRentFromList()
                    }
                    .setNegativeButton("Odustani") { dialog, _ ->
                        dialog.cancel();
                    }
                    .show()
                return@setOnClickListener
            }
        }

        private fun removeRentFromList() {
            rentLists.removeAt(adapterPosition)
            notifyItemRemoved(adapterPosition)
        }

        @SuppressLint("SetTextI18n")
        fun bind(rent: Rent) {
            rentPersonName.text = "${rent.tenant?.name} ${rent.tenant?.surname}";
            rentPersonAddress.text = rent.tenant?.flat?.address.toString();
            rentPersonDateOfMovingIn.text = "since. ${rent.tenant?.dateOfMovingIn}"
            //rentDueMonth.text = rent.dueMonth.toString()
            rentDueMonth.text = formatMonth(rent.month_to_be_paid).substring(0,3).uppercase()
            rentDueYear.text = rent.year_to_be_paid.toString()
            //setDueMonthYearColor(rent.paid)
            rentAmount.text = "${rent.tenant?.flat?.amount.toString()} €"
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