package hr.foi.rampu.stanarko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Registracija : AppCompatActivity() {

    val signUp = findViewById<Button>(R.id.btn_register);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registracija)
        signUp.setOnClickListener{
            PasswordCheck();
        }
    }

    fun PasswordCheck(){
        var password = findViewById<EditText>(R.id.et_password);
        var confirmPassword = findViewById<EditText>(R.id.et_confirm_password);
        if(password.text===confirmPassword.text){
            Toast.makeText(baseContext,"Password correct!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(baseContext,"Password incorrect!",Toast.LENGTH_SHORT).show();
        }
    }
}