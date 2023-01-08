package hr.foi.rampu.stanarko

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.adapters.FlatsAdapter
import hr.foi.rampu.stanarko.helpers.MockDataLoader
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_flat_list)
        recyclerView.adapter = runBlocking { FlatsAdapter(MockDataLoader.getFirebaseFlats()) }
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}