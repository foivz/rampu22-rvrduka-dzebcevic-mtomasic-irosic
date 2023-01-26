package hr.foi.rampu.stanarko.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QuerySnapshot
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.adapters.RentsAdapter
import hr.foi.rampu.stanarko.database.RentsDAO
import hr.foi.rampu.stanarko.entities.Rent
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


class PaidRentFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_paid_rent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rentsDAO = RentsDAO()

        recyclerView = view.findViewById(R.id.rv_paid_rent)
        recyclerView.adapter = runBlocking { RentsAdapter(getRents() as MutableList<Rent>) }
        recyclerView.layoutManager = LinearLayoutManager(view.context)

       parentFragmentManager.setFragmentResultListener("rent_paid", viewLifecycleOwner) { _, bundle ->
           val addedRentId = bundle.getInt("rentId")
           val addedRentMonthDue = bundle.getInt("dueMonth")
           val addedRentYearDue = bundle.getInt("dueYear")

           val rentsAdapter = recyclerView.adapter as RentsAdapter

           suspend fun getSnapshot(): QuerySnapshot {
               val rentsRef = rentsDAO.getAllRentByIDDueMonthYear(addedRentId, addedRentMonthDue, addedRentYearDue).get()
               return rentsRef.await()
           }

           suspend fun getRent(): Rent? {
               val snapshot = getSnapshot()
               val documents = snapshot.documents
               return documents[0].toObject(Rent::class.java)
           }

           val rent = runBlocking {getRent()}
           if (rent != null) {
               rentsAdapter.addRentToList(rent)
           }
        }
    }
    private suspend fun getRents(): List<Rent>{
        val currentUser = "markomarkic@gmail.com"
        val rentsDAO = RentsDAO()
        val rents = mutableListOf<Rent>()
        val result = rentsDAO.getAllRentsByMail(currentUser,true).await()
        rents.addAll(result.toObjects(Rent::class.java))
        return rents
    }
}