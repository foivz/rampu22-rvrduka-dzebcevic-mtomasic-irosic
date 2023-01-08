package hr.foi.rampu.stanarko.F01_Registracija

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import hr.foi.rampu.stanarko.R

class Registracija : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registracija)
        val signUp = findViewById<Button>(R.id.btn_register)
        signUp.setOnClickListener{
            RegisterUser()
        }
    }

    fun RegisterUser(){
        val name = findViewById<EditText>(R.id.et_name).text.toString()
        val surname = findViewById<EditText>(R.id.et_surname).text.toString()
        val phoneNumber = findViewById<EditText>(R.id.et_phone_number).text.toString()
        val mail = findViewById<EditText>(R.id.et_mail).text.toString()
        val password = findViewById<EditText>(R.id.et_password).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.et_confirm_password).text.toString()

        if (!BlankCheck(name, surname, phoneNumber, mail, password, confirmPassword)) return
        if(!PhoneNumberCheck(phoneNumber)) return
        if(!MailCheck(mail)) return
        if(!PasswordCheck(password,confirmPassword)) return
    }

    fun BlankCheck(name: String, surname: String, phoneNumber: String, mail: String, password: String, confirmPassword: String): Boolean {
        if (name.isBlank()||surname.isBlank()||phoneNumber.isBlank()||mail.isBlank()||password.isBlank()||confirmPassword.isBlank()){
            Toast.makeText(baseContext,getString(R.string.blank_register_fields),Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun PhoneNumberCheck(phoneNumber: String): Boolean{
        val pattern = Regex("^([\\d]{10,10})\$")
        if (pattern.matches(phoneNumber))  return true
        Toast.makeText(baseContext,getString(R.string.incorrect_phone_number),Toast.LENGTH_SHORT).show()
        return false
    }

    fun MailCheck(mail: String): Boolean {
        val pattern = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (pattern.matches(mail)) return true
        Toast.makeText(baseContext,getString(R.string.incorrect_mail_register),Toast.LENGTH_SHORT).show()
        return false
    }

    fun PasswordCheck(password : String, confirmPassword : String): Boolean {
        if(password==confirmPassword)
            return true
        Toast.makeText(baseContext,getString(R.string.incorrect_register_password),Toast.LENGTH_SHORT).show()
        return false
    }
}