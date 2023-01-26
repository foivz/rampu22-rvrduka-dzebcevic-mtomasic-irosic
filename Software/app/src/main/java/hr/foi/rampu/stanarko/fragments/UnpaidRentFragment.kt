package hr.foi.rampu.stanarko.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.adapters.RentsAdapter
import hr.foi.rampu.stanarko.database.RentsDAO
import hr.foi.rampu.stanarko.entities.Rent


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
        val currentUser = "markomarkic@gmail.com"
        val rentsDAO = RentsDAO()

        rentsDAO.getAllRentsByMail(currentUser,false)
            .addOnSuccessListener { snapshot ->
                val rents = snapshot.toObjects(Rent::class.java)
                val rentsAdapter = RentsAdapter(rents.toMutableList()) { rentId, dueMonth, dueYear ->
                    val bundle = Bundle()
                    val additionalValues = mapOf("rentId" to rentId, "dueMonth" to dueMonth, "dueYear" to dueYear)
                    for ((key, value) in additionalValues) {
                        bundle.putInt(key, value)
                    }
                    parentFragmentManager.setFragmentResult("rent_paid", bundle)
                }

                recyclerView.adapter = rentsAdapter
            }
            .addOnFailureListener { exception ->
                // Handle the exception
            }
        recyclerView = view.findViewById(R.id.rv_unpaid_rent)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }
}

