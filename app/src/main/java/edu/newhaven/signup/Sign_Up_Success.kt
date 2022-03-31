package edu.newhaven.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class Sign_Up_Success : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign__up__success)

        val signedUp_email = intent.getStringExtra("Email")
        Log.d("Ajieth","SignedUp email" + signedUp_email)
    }
}