package hr.foi.rampu.stanarko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.entities.Flat
import hr.foi.rampu.stanarko.helpers.DataLoader
import kotlinx.coroutines.runBlocking


class FlatsAdapter(private val flatsList : List<Flat>) : RecyclerView.Adapter<FlatsAdapter.FlatViewHolder>() {
    inner class FlatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val flatId: TextView
        private val flatAdress: TextView
        private val flatOccupied: TextView
        private val tenants: RecyclerView
        private val expand: ImageButton

        init {
            flatId = view.findViewById(R.id.tv_flat_id)
            flatAdress = view.findViewById(R.id.tv_flat_adress)
            flatOccupied = view.findViewById(R.id.tv_flat_occupied)
            tenants = view.findViewById(R.id.rv_tenant_list)
            expand = view.findViewById(R.id.ib_expand)
        }
        fun bind(flat: Flat) {
            flatId.text = flat.id.toString()
            flatAdress.text = flat.address
            val firebaseTenants = runBlocking { DataLoader.getFirebaseTenants(flat.id) }
            if(firebaseTenants.isEmpty()){
                expand.visibility = View.GONE
                flatOccupied.text = tenants.context.getString(R.string.flat_free)
            }
            else{
                flatOccupied.text = tenants.context.getString(R.string.flat_occupied)
            }
            tenants.adapter = TenantsAdapter(firebaseTenants)
            tenants.layoutManager = LinearLayoutManager(tenants.context)
            tenants.visibility = View.GONE

            expand.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                // to gone and change the expand less icon to expand more.
                if (tenants.visibility == View.VISIBLE) {
                    // The transition of the hiddenView is carried out by the TransitionManager class.
                    // Here we use an object of the AutoTransition Class to create a default transition
                    tenants.visibility = View.GONE
                    expand.setImageResource(R.drawable.ic_baseline_expand_more_24)
                } else {
                    tenants.visibility = View.VISIBLE
                    expand.setImageResource(R.drawable.ic_baseline_expand_less_24)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlatViewHolder {
        val taskView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.flat_item, parent, false)
        return FlatViewHolder(taskView)
    }

    override fun onBindViewHolder(holder: FlatViewHolder, position: Int) {
        holder.bind(flatsList[position])
    }

    override fun getItemCount(): Int {
        return flatsList.size
    }
}