package hr.foi.rampu.stanarko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.entities.Flat
import hr.foi.rampu.stanarko.helpers.MockDataLoader
import kotlinx.coroutines.runBlocking


class FlatsAdapter(private val flatsList : List<Flat>) : RecyclerView.Adapter<FlatsAdapter.FlatViewHolder>() {
    inner class FlatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val flatId: TextView
        private val flatAdress: TextView
        private val flatOccupied: TextView
        private val tenants: RecyclerView

        init {
            flatId = view.findViewById(R.id.tv_flat_id)
            flatAdress = view.findViewById(R.id.tv_flat_adress)
            flatOccupied = view.findViewById(R.id.tv_flat_occupied)
            tenants = view.findViewById(R.id.rv_tenant_list)
        }
        fun bind(flat: Flat) {
            flatId.text = flat.id.toString()
            flatAdress.text = flat.address
            flatOccupied.text = when(flat.occupied) {
                false -> "Free" //nisam siguran kako dohvatiti kontekst te onda ne hardkodirane stringove
                true -> "Occupied"
            }
            tenants.adapter = runBlocking { TenantsAdapter(MockDataLoader.getFirebaseTenants(flat.id)) }
            tenants.layoutManager = LinearLayoutManager(tenants.context)
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