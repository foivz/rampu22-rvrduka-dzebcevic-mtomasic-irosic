package hr.foi.rampu.stanarko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.firebase.auth.FirebaseAuth
import hr.foi.rampu.stanarko.adapters.FlatsAdapter
import hr.foi.rampu.stanarko.database.FlatsDAO
import hr.foi.rampu.stanarko.database.OwnersDAO
import hr.foi.rampu.stanarko.entities.Flat
import hr.foi.rampu.stanarko.entities.Owner
import hr.foi.rampu.stanarko.helpers.MockDataLoader
import kotlinx.coroutines.runBlocking

class AddFlatActivity : AppCompatActivity() {

    var maxID: Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flat)
        val dugme = findViewById<Button>(R.id.btnAddFlat)
        Load()
        dugme.setOnClickListener{
            AddFunction()
        }
    }

    fun Load(){
        var cmbOkupiran = findViewById<Spinner>(R.id.spinnerOccupied)

        val izbori = arrayOf("Yes", "No")
        val spinnerAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, izbori)
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbOkupiran.adapter = spinnerAdapter2
    }

    fun AddFunction() {

        val adresa = findViewById<EditText>(R.id.etAddress).text.toString()
        val grad = findViewById<EditText>(R.id.etCity).text.toString()
        val posta = findViewById<EditText>(R.id.etPostalCode).text.toString().toInt()
        var cmbOkupiran = findViewById<Spinner>(R.id.spinnerOccupied)
        val amount = findViewById<EditText>(R.id.etAmount).text.toString().toDouble()

        val flat1 = FlatsDAO();

        flat1.getAllFlats().addOnSuccessListener { snapshot ->
            var temp = snapshot.toObjects(Flat::class.java)

            var flatOccupied = true
            if(cmbOkupiran.selectedItem.toString() == "No"){
                flatOccupied=false
            }


            var prijavljen = FirebaseAuth.getInstance().currentUser?.email
            if (prijavljen != null) {

                var help = OwnersDAO()

                help.getOwnerByEmail(prijavljen).addOnSuccessListener { snapshot ->

                    var trenutnoPrijavljeni = snapshot.toObjects(Owner::class.java)[0]
                    val novi = Flat(1,adresa,grad,trenutnoPrijavljeni,flatOccupied, amount, posta)
                    val dodavanje = FlatsDAO()
                    dodavanje.AddFlat(novi)

                }

            } else {
                Log.d("DADA", "NULL JE")
            }

            var help = FlatsDAO()
            help.getAllFlats().addOnSuccessListener { snapshot ->
                var a =  snapshot.toObjects(Flat::class.java)
                var b =FlatsAdapter(a)
                b.refresh()
                val intent = Intent(this, MainActivity::class.java);
                startActivity(intent)
            }

        }

    }

}