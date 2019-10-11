package com.example.studentsforum

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.studentsforum.helpers.DatabaseHelper
import com.example.studentsforum.model.Users

class ViewUser : AppCompatActivity() {

    private  lateinit var tv_name:TextView
    private  lateinit var tv_email:TextView
    private  lateinit var tv_address:TextView
    private  lateinit var tv_password:TextView
    private  lateinit var im_Image:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user)

        val  id = intent.getIntExtra("id",-1)
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val address = intent.getStringExtra("address")
        val password = intent.getStringExtra("password")
        val image = intent.getByteArrayExtra("image")

        tv_name = findViewById(R.id.VU_name)
        tv_email = findViewById(R.id.VU_email)
        tv_address = findViewById(R.id.VU_address)
        tv_password = findViewById(R.id.VU_password)
        im_Image = findViewById(R.id.VU_image)

        tv_name.setText(name)
        tv_email.setText(email)
        tv_address.setText(address)
        tv_password.setText(password)

        // Convert bytes data into a Bitmap
        val bmp = BitmapFactory.decodeByteArray(image,0,image.size)
        im_Image.setImageBitmap(bmp)

        val update_bt : Button = findViewById(R.id.btUpdate_user)
        val delete_bt : Button = findViewById(R.id.bt_delete_user)

        update_bt.setOnClickListener(View.OnClickListener {
            val toUpdate = Intent(this,UpdateUser::class.java)

            //sends id, name, email, address and password to the next page
            toUpdate.putExtra("id", id)
            toUpdate.putExtra("name",name)
            toUpdate.putExtra("email", email)
            toUpdate.putExtra("address", address)
            toUpdate.putExtra("password",password)
            toUpdate.putExtra("image",image)

            startActivity(toUpdate)

        })



        delete_bt.setOnClickListener(View.OnClickListener {
            val db_helper = DatabaseHelper(this)
            val users = Users(id = id, name = "", email = "", password = "", address = "", image = ByteArray(image.size))
            db_helper.deleteUser(users)

            val  toMain = Intent(this,MainActivity::class.java)
            toMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            toMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(toMain)
            finish()
            Toast.makeText(this,"User Deleted successfully",Toast.LENGTH_LONG).show()

        })
    }
}
