package com.example.studentsforum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val submmit: Button = findViewById(R.id.bt_signup_submit)

        submmit.setOnClickListener(View.OnClickListener {
            var enter =Intent(this,welcome::class.java)
            startActivity(enter)
        })

    }
}
