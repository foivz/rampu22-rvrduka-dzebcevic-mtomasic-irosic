package hr.foi.rampu.stanarko.F01_Registracija

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import hr.foi.rampu.stanarko.R
import hr.foi.rampu.stanarko.database.TenantsDAO
import hr.foi.rampu.stanarko.entities.Tenant

class Registracija : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registracija)
        val signUp = findViewById<Button>(R.id.btn_register)
        signUp.setOnClickListener{
            registerUser()
        }
    }

    private fun registerUser(){
        val name = findViewById<EditText>(R.id.et_name).text.toString()
        val surname = findViewById<EditText>(R.id.et_surname).text.toString()
        val phoneNumber = findViewById<EditText>(R.id.et_phone_number).text.toString()
        val mail = findViewById<EditText>(R.id.et_mail).text.toString()
        val password = findViewById<EditText>(R.id.et_password).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.et_confirm_password).text.toString()

        if (!blankCheck(name, surname, phoneNumber, mail, password, confirmPassword)) return
        if(!phoneNumberCheck(phoneNumber)) return
        if(!mailCheck(mail)) return
        if(!passwordCheck(password,confirmPassword)) return

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    val tenant = Tenant(
                        name = name,
                        surname = surname,
                        phoneNumber = phoneNumber,
                        mail = mail,
                        password = password,
                        flat = null,
                        dateOfMovingIn = null
                    )
                    TenantsDAO().createTenant(tenant,this)
                } else {
                    // Registration failed
                    Toast.makeText(this,"Registracija neuspješna", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun blankCheck(name: String, surname: String, phoneNumber: String, mail: String, password: String, confirmPassword: String): Boolean {
        if (name.isBlank()||surname.isBlank()||phoneNumber.isBlank()||mail.isBlank()||password.isBlank()||confirmPassword.isBlank()){
            Toast.makeText(baseContext,getString(R.string.blank_register_fields),Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun phoneNumberCheck(phoneNumber: String): Boolean{
        val pattern = Regex("^([\\d]{10,10})\$")
        if (pattern.matches(phoneNumber))  return true
        Toast.makeText(baseContext,getString(R.string.incorrect_phone_number), Toast.LENGTH_SHORT).show()
        return false
    }

    private fun mailCheck(mail: String): Boolean {
        val pattern = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (pattern.matches(mail)) return true
        Toast.makeText(baseContext,getString(R.string.incorrect_mail_register), Toast.LENGTH_SHORT).show()
        return false
    }

    private fun passwordCheck(password : String, confirmPassword : String): Boolean {
        if(password==confirmPassword)
            return true
        Toast.makeText(baseContext,getString(R.string.incorrect_register_password), Toast.LENGTH_SHORT).show()
        return false
    }
}