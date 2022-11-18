package hr.foi.rampu.stanarko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Registracija : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registracija)
        val signUp = findViewById<Button>(R.id.btn_register);
        signUp.setOnClickListener{
            RegisterUser();
        }
    }

    fun RegisterUser(){
        val name = findViewById<EditText>(R.id.et_name).text.toString();
        val surname = findViewById<EditText>(R.id.et_surname).text.toString();
        val phoneNumber = findViewById<EditText>(R.id.et_phone_number).text.toString();
        val mail = findViewById<EditText>(R.id.et_mail).text.toString();
        val password = findViewById<EditText>(R.id.et_password).text.toString();
        val confirmPassword = findViewById<EditText>(R.id.et_confirm_password).text.toString();

        if (name.isBlank()||surname.isBlank()||phoneNumber.isBlank()||mail.isBlank()||password.isBlank()||confirmPassword.isBlank())
            Toast.makeText(baseContext,getString(R.string.blank_register_fields),Toast.LENGTH_SHORT).show();

        if(!PasswordCheck(password,confirmPassword)) return;
    }

    fun PasswordCheck(password : String, confirmPassword : String): Boolean {
        var check = true;
        if(password!=confirmPassword){
            Toast.makeText(baseContext,getString(R.string.incorrect_register_password),Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }
}