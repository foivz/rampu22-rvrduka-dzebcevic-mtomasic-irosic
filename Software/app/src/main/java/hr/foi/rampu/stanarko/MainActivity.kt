package hr.foi.rampu.stanarko

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        val tenants = hashMapOf(
            "id" to 1,
            "name" to "John",
            "surname" to "Doe",
            "phoneNumber" to "0997824782",
            "mail" to "johndoe@gmail.com",
            "role" to 1
        )

        db.collection("tenants").add(tenants).addOnSuccessListener {
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "Failure", Toast.LENGTH_LONG).show()
        }
        */
    }
}