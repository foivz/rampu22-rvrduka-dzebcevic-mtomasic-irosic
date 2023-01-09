package hr.foi.rampu.stanarko.F02_Prijava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import hr.foi.rampu.stanarko.R

class Prijava : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prijava)
        val loginButton = findViewById<Button>(R.id.btn_login)
        loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val mail = findViewById<EditText>(R.id.et_mail).text.toString()
        val password = findViewById<EditText>(R.id.et_password).text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(mail,password).addOnSuccessListener {
            Toast.makeText(this,"Successful login",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Invalid login",Toast.LENGTH_SHORT).show()
        }
    }
}