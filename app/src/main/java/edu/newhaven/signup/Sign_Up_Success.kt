package edu.newhaven.signup

/* -------------------------------------------------------------------------------------
 Ajieth: SignUp Success Activity for showing the user that the signUp was successful
--------------------------------------------------------------------------------------------*/
import android.app.Dialog
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.data.model.Resource

class Sign_Up_Success : AppCompatActivity() {

    /* ------------------------------------------------------------
           Intializers
    ----------------------------------------------------------------*/
    lateinit var email: TextView
    lateinit var first_name: TextView
    lateinit var website: TextView
    lateinit var title: TextView
    lateinit var signIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign__up__success)

        supportActionBar?.hide()

        val signedUp_email = intent.getStringExtra("Email")
        val signedUp_first_name = intent.getStringExtra("First_Name")
        val signedUp_website = intent.getStringExtra("Website")

        Log.d("Ajieth","SignedUp email" + signedUp_email + signedUp_first_name + signedUp_website)

        title = findViewById<TextView>(R.id.signedUp_Title)
        title.setText( getString(R.string.signedUp_Title , signedUp_first_name))

        email = findViewById<TextView>(R.id.signedUp_email)
        first_name = findViewById<TextView>(R.id.signedUp_first_name)
        website = findViewById<TextView>(R.id.signedUp_website)

        signIn = findViewById<Button>(R.id.signIn_button)

        email.setText(signedUp_email)
        first_name.setText(signedUp_first_name)
        website.setText(signedUp_website)

        signIn.setOnClickListener {
            showDialog("SignIn Will be Avaliable Soon")
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