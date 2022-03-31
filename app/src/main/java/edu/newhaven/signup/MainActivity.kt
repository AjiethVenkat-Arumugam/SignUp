package edu.newhaven.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
//    lateinit var email: String
//    lateinit var password: String
//    lateinit var first_name: String
//    lateinit var website: String
    var flagUp_register_Success: Boolean = false
    var flagUp_storeData_Success: Boolean = false
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val signUp_button = findViewById<Button>(R.id.signUp_button)

        signUp_button.setOnClickListener{
            val email = findViewById<EditText>(R.id.signUp_email).text.toString()
            val password = findViewById<EditText>(R.id.signUp_password).text.toString()
            val first_name = findViewById<EditText>(R.id.signUp_firstName).text.toString()
            val website = findViewById<EditText>(R.id.signUp_website).text.toString()

            Log.d("Ajieth", "Data" + email + password + first_name + website)

            registerUser(email, password, first_name, website)

            Log.d("Ajieth", "Data 4" + flagUp_register_Success + flagUp_storeData_Success)

        }
    }

    private fun registerUser(
        p_email: String,
        p_password: String,
        p_first_name: String,
        p_website: String
    ) {
        Log.d("Ajieth", "Data 2" + p_email + p_password)
        auth.createUserWithEmailAndPassword(p_email, p_password).addOnSuccessListener {
            Toast.makeText(applicationContext, "SignUp Successful", Toast.LENGTH_LONG).show()
            uploadData(p_first_name, p_email, p_website)
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "SignUp Failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadData(f_name: String, f_email: String, f_website: String) {
        Log.d("Ajieth", "Data 3" + f_email + f_name + f_website)
        database = FirebaseDatabase.getInstance().getReference("Users")
        val User = User(f_name, f_email, f_website)
        database.child(f_name).setValue(User).addOnSuccessListener {

            Log.d("Ajieth", "Data 4" + f_email + f_name + f_website)
            val intent =  Intent(this, Sign_Up_Success::class.java)
            intent.putExtra("Email", f_email)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "Network Error", Toast.LENGTH_LONG).show()
        }
    }
}