package edu.newhaven.signup

/* -------------------------------------------------------------------------------------------------
 Ajieth: Main Activity for SignUp with interagration of Firebase Auth and Firebase RealTime Storage
----------------------------------------------------------------------------------------------------*/

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Collections.replaceAll

class MainActivity : AppCompatActivity() {

    /* ------------------------------------------------------------
            Intializers
    ----------------------------------------------------------------*/

    private val auth = FirebaseAuth.getInstance()
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var first_name: EditText
    lateinit var website: EditText
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val signUp_button = findViewById<Button>(R.id.signUp_button)

        signUp_button.setOnClickListener {
            email = findViewById<EditText>(R.id.signUp_email)
            password = findViewById<EditText>(R.id.signUp_password)
            first_name = findViewById<EditText>(R.id.signUp_firstName)
            website = findViewById<EditText>(R.id.signUp_website)

            registerUser(email.text.toString().replace(" ", ""), password.text.toString().replace(" ", ""), first_name.text.toString(), website.text.toString())
        }
    }

    /* --------------------------------------------------------------------------------------------------
        Register User in Firebase Auth with Email and Password (Email Duplicate not allowed)
    -----------------------------------------------------------------------------------------------------*/

    private fun registerUser(p_email: String, p_password: String, p_first_name: String, p_website: String) {
        auth.createUserWithEmailAndPassword(p_email, p_password).addOnSuccessListener {
            Toast.makeText(applicationContext, "SignUp Successful", Toast.LENGTH_LONG).show()
            uploadData(p_first_name, p_email, p_website)
        }.addOnFailureListener { exception ->
            when (exception) {
                is FirebaseAuthUserCollisionException -> showDialog("E-mail address is not available. Please try again")
                is FirebaseNetworkException -> showDialog("No network.")
                else -> showDialog("Try again later.")
            }
        }
    }

    /* ------------------------------------------------------------
        Upload data into Firebase Storage as backend for collecting user data
    ----------------------------------------------------------------*/

    private fun uploadData(f_name: String, f_email: String, f_website: String) {
        database = FirebaseDatabase.getInstance().getReference("Users")
        val User = User(f_name, f_email, f_website)
        database.child(f_name).setValue(User).addOnSuccessListener {

            val intent = Intent(this, Sign_Up_Success::class.java)
            intent.putExtra("Email", f_email)
            intent.putExtra("First_Name", f_name)
            intent.putExtra("Website", f_website)
            startActivity(intent)

            email.text.clear()
            first_name.text.clear()
            password.text.clear()
            website.text.clear()

        }.addOnFailureListener {
            Toast.makeText(applicationContext, "Network Error", Toast.LENGTH_LONG).show()
        }
    }

    /* ------------------------------------------------------------
        Custom Alert dialog for exception handling
    ----------------------------------------------------------------*/

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialog)
        val body = dialog.findViewById(R.id.alertTitle) as TextView
        body.text = title
        val noBtn = dialog.findViewById(R.id.button_cancel) as TextView
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}

/* ------------------------------------------------------------
    End of Line
----------------------------------------------------------------*/