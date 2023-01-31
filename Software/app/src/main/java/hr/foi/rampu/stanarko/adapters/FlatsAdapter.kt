package hr.foi.rampu.stanarko.adapters

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.MainActivity
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.database.FlatsDAO
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.entities.Flat
import hr.foi.rampu.stanarko.entities.Tenant
import hr.foi.rampu.stanarko.helpers.MockDataLoader
import kotlinx.coroutines.runBlocking


class FlatsAdapter(private var flatsList: MutableList<Flat> ) : RecyclerView.Adapter<FlatsAdapter.FlatViewHolder>() {
    inner class FlatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val flatId: TextView
        private val flatAdress: TextView
        private val flatOccupied: TextView
        private var tenants: RecyclerView
        private val expand: ImageButton
        private val delete: ImageButton
        private val add_tenant: ImageButton

        init {
            flatId = view.findViewById(R.id.tv_flat_id)
            flatAdress = view.findViewById(R.id.tv_flat_adress)
            flatOccupied = view.findViewById(R.id.tv_flat_occupied)
            tenants = view.findViewById(R.id.rv_tenant_list)
            expand = view.findViewById(R.id.ib_expand)
            delete = view.findViewById(R.id.ib_delete)
            add_tenant = view.findViewById(R.id.ib_add_tenant)
        }
        fun bind(flat: Flat) {
            flatId.text = flat.id.toString()
            flatAdress.text = flat.address
            flatOccupied.text = when(flat.occupied) {
                false -> "Free"
                true -> "Occupied"
            }
            val firebaseTenants = runBlocking { MockDataLoader.getFirebaseTenants(flat.id) }
            if(firebaseTenants.isEmpty()){
                expand.visibility = View.GONE
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

            add_tenant.setOnClickListener() {
                val newTenantDialog = LayoutInflater
                    .from(flatId.context)
                    .inflate(R.layout.add_tenant_dialog, null)
                AlertDialog.Builder(flatId.context)
                    .setView(newTenantDialog)
                    .setTitle(flatId.context.getString(R.string.add_tenant))

                    .setPositiveButton("Add new tenant") { _, _ ->
                        var emailAddress = newTenantDialog.findViewById<EditText>(R.id.et_tenant_mail)
                        var helperVariable = TenantsDAO()
                        helperVariable.changeFlatOfTenant(emailAddress.text.toString(), flat)

                    }
                    .show()

            }

            delete.setOnClickListener{

                var delete = FlatsDAO()
                delete.removeFlat("address", flat.address, flat.id){result ->
                    if(result == 1){
                        val indexToRemove = flatsList.indexOfFirst { it.address == flat.address }
                        flatsList.removeAt(indexToRemove)
                        notifyDataSetChanged()
                    }
                    else{
                        Log.d("GRESKA", "GRESKA")
                    }
                }
                notifyDataSetChanged()
            }
        }
    }

    fun refresh(){
        notifyDataSetChanged()
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