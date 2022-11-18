package hr.foi.rampu.stanarko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.entities.*

class RentsAdapter(
    private val rentList: List<Rent>
) : RecyclerView.Adapter<RentsAdapter.RentViewHolder>(){
    inner class RentViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val rentName: TextView
        private val rentMonth: TextView
        private val rentYear: TextView
        init{
            rentName = view.findViewById(R.id.tv_rent_name)
            rentMonth = view.findViewById(R.id.tv_rent_month)
            rentYear = view.findViewById(R.id.tv_rent_year)
        }
        fun bind(rent: Rent) {
            rentName.text = rent.id.toString();
            rentMonth.text = rent.month_to_be_paid.toString();
            rentYear.text = rent.year_to_be_paid.toString();
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentViewHolder {
        val taskView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.single_rent, parent, false)
        return RentViewHolder(taskView)
    }

    override fun onBindViewHolder(holder: RentViewHolder, position: Int) {
        holder.bind(rentList[position])
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
