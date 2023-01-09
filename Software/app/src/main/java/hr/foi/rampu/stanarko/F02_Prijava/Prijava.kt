package hr.foi.rampu.stanarko.F02_Prijava

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import hr.foi.rampu.stanarko.F01_Registracija.Registracija
import hr.foi.rampu.stanarko.R

class Prijava : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prijava)
        val loginButton = findViewById<Button>(R.id.btn_login)
        loginButton.setOnClickListener {
            loginUser()
        }
        val spannable = SpannableString("Don't have an account? Register")
        val span = object : ClickableSpan(){
            override fun onClick(widget: View) {
                val intent = Intent(this@Prijava, Registracija::class.java)
                startActivity(intent)
            }
        }
        spannable.setSpan(span,23,31,0)
        val register = findViewById<TextView>(R.id.register_prompt)
        register.text = spannable
        register.movementMethod = LinkMovementMethod.getInstance()
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