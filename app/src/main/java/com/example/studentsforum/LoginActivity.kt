package com.example.studentsforum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tv_new_user = findViewById<View>(R.id.tv_not_registered) as TextView

        val tv_forgot_password: TextView = findViewById(R.id.tv_forgot_password)

        val et_login_email:EditText = findViewById(R.id.et_login_email)

        val et_login_password:EditText = findViewById(R.id.et_login_password)

        val bt_submit_login : Button = findViewById(R.id.bt_login_submit)

        bt_submit_login.setOnClickListener(View.OnClickListener {
            val email :String = et_login_email.text.toString().trim()
            val password :String = et_login_password.text.toString().trim()


            if (email.isEmpty()){
                et_login_email.setError("Email is Empty")

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_login_email.setError("invalid email ")}

            else if (password.isEmpty())
            {
                et_login_password.setError("password empty")
            }   else if (password.length<8){

                et_login_password.setError("password too Short")
            }   else{
                Toast.makeText(this,"all fields are valid", Toast.LENGTH_SHORT).show()
            }
        })


        tv_new_user.setOnClickListener(View.OnClickListener {
            var  Signup = Intent(this,Signup::class.java)

            startActivity(Signup)

        })
        tv_forgot_password.setOnClickListener(){
            var forgot = Intent(this,ForgotPassword::class.java)
            startActivity(forgot)
        }
    }
}
