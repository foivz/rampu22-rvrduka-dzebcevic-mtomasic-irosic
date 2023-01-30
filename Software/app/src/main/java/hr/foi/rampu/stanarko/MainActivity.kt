package hr.foi.rampu.stanarko

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.stanarko.NavigationDrawer.OwnerDrawerActivity
import hr.foi.rampu.stanarko.adapters.FlatsAdapter
import hr.foi.rampu.stanarko.databinding.ActivityMainBinding
import hr.foi.rampu.stanarko.helpers.MockDataLoader
import kotlinx.coroutines.runBlocking


class MainActivity : OwnerDrawerActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("MainActivity")

        recyclerView = findViewById(R.id.rv_flat_list)
        recyclerView.adapter = runBlocking { FlatsAdapter(MockDataLoader.getFirebaseFlats()) }
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}