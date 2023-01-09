package hr.foi.rampu.stanarko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.adapters.FlatsAdapter
import hr.foi.rampu.stanarko.helpers.MockDataLoader
import kotlinx.coroutines.runBlocking

class FlatListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat_list)

        recyclerView = findViewById(R.id.rv_flat_list)
        recyclerView.adapter = runBlocking { FlatsAdapter(MockDataLoader.getFirebaseFlats()) }
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}