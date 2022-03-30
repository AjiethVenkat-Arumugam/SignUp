package edu.newhaven.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.auth.FirebaseAuth
import javax.microedition.khronos.egl.EGLDisplay

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    lateinit var email: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        email = findViewById<EditText>(R.id.signUp_email)
        password = findViewById<EditText>(R.id.signUp_password)

        val signUp_button = findViewById<Button>(R.id.signUp_button)

        signUp_button.setOnClickListener{
            registerUser()
        }
    }

    private fun registerUser(){
        Log.d("Ajieth","Register Detials" + email.text.toString() + password.text.toString())
        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(this) {task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext, "SignUp Successful", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext, "SignUp Failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}