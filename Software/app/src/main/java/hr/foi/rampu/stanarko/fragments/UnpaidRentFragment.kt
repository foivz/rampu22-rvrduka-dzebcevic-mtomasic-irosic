package hr.foi.rampu.stanarko.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.adapters.RentsAdapter
import hr.foi.rampu.stanarko.database.FlatsDAO
import hr.foi.rampu.stanarko.database.RentsDAO
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.entities.Rent
import hr.foi.rampu.stanarko.helpers.MockDataLoader


class UnpaidRentFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_unpaid_rent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentUserID = 1

        val tenantDao = TenantsDAO()
        val rentsDAO = RentsDAO()
        val flatDAO = FlatsDAO()

        rentsDAO.getAllRentsByTenantID(currentUserID,false)
            .addOnSuccessListener { snapshot ->
                val rents = snapshot.toObjects(Rent::class.java)
                recyclerView.adapter = RentsAdapter(rents)
            }
            .addOnFailureListener { exception ->
                // Handle the exception
            }
        recyclerView = view.findViewById(R.id.rv_unpaid_rent)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }
}