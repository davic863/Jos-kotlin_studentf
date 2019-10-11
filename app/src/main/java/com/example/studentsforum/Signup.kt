package com.example.studentsforum

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.studentsforum.helpers.DatabaseHelper
import com.example.studentsforum.model.Users
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.users_list_card.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class Signup : AppCompatActivity() {

    private lateinit var picture_display: ImageView
    //two constants to specify our actions, either we are picking images from gallery or camera
    private val GALLERY = 10
    private val CAMERA = 21

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val takePicture: TextView = findViewById(R.id.tv_upload)
        val btSingup: Button = findViewById(R.id.bt_signup_submit)
        val tvname: EditText = findViewById(R.id.et_signup_name)
        val tvemail: EditText = findViewById(R.id.et_sign_email)
        val tvpassword: EditText = findViewById(R.id.et_signup_password)
        val tvconpassword: EditText = findViewById(R.id.et_signup_confm_password)
        val tvaddress: EditText = findViewById(R.id.et_signup_Address)

        picture_display = findViewById(R.id.im_person)

        takePicture.setOnClickListener(View.OnClickListener {
            //create an object of the alert dialog
            val pictureDialog = AlertDialog.Builder(this)

            // we set out title
            pictureDialog.setTitle("Select Action")

            //we specify the options on this line
            val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
            //we set our actions here. if user select any option what should it do
            pictureDialog.setItems(pictureDialogItems
            ) { dialog, which ->
                when (which) {
                    //action 1 chooses image from the gallery
                    0 -> choosePhotoFromGallary()//this function that performs the action is below
                    //action 2 takes a photo from the camera
                    1 -> takePhotoFromCamera()//this function that perform this action is below
                }
            }
            //always put this line for the dialog to show
            pictureDialog.show()


        })

        btSingup.setOnClickListener(View.OnClickListener {

            val name: String = tvname.text.toString().trim()
            val email: String = tvemail.text.toString().trim()
            val password: String = tvpassword.text.toString().trim()
            val conpassword: String = tvconpassword.text.toString().trim()
            val address: String = tvaddress.text.toString().trim()
            val profileimage:ImageView=findViewById(R.id.im_person)


            val mobile: CheckBox = findViewById(R.id.cb_mobile_2)
            val ARVR: CheckBox = findViewById(R.id.cb_arvr)
            val Digmrkt: CheckBox = findViewById(R.id.cb_digital)
            val bakend: CheckBox = findViewById(R.id.cb_bkend_1)
            val git: CheckBox = findViewById(R.id.cb_git)
            var id: Int = gender_group.checkedRadioButtonId

            val bitmap = (profileimage.getDrawable() as BitmapDrawable).getBitmap()
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val image = stream.toByteArray()

            Log.i("value", "$image")

            if (mobile.isChecked || ARVR.isChecked || Digmrkt.isChecked || bakend.isChecked || git.isChecked) {


                if (id != -1) {

                    if (name.isEmpty()) {
                        tvname.setError("Name is empty")

                    } else if (email.isEmpty()) {
                        tvemail.setError("Email field is blank")

                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        tvemail.setError("invalid email ")

                    } else if (password.isEmpty()) {

                        tvpassword.setError("password missing")

                    } else if (password.length < 6) {
                        tvpassword.setError("password too weak")

                    } else if (conpassword.isEmpty()) {
                        tvconpassword.setError("confirm Password")

                    } else if (conpassword != password) {
                        tvconpassword.setError("password miss match")

                    } else if (address.isEmpty()) {
                        tvaddress.setError("Address is Empty")
                    } else {

                        // create the Database helper instance to push your form data

                        val databaseHelper = DatabaseHelper(this)

                        if (!databaseHelper.checkUser(email)) {
                            val user = Users(
                                name = name,
                                email = email,
                                password = password,
                                address = address,
                                image = image )

                            databaseHelper.addUser(user)

                            /* databaseHelper.addUser(user)
*/
                            Toast.makeText(this, "Sign up Succesful", Toast.LENGTH_LONG).show()

                            val toLogin = Intent(this, LoginActivity::class.java)
                            startActivity(toLogin)
                            finish()
                        } else {
                            Toast.makeText(this, "User already exist", Toast.LENGTH_LONG).show()
                        }
                    }    // If no radio button checked in this radio group
                } else {
                    Toast.makeText(this, "check Gender Button", Toast.LENGTH_LONG).show()
                }
            } else {

                Toast.makeText(this, "Track  is unchecked", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun choosePhotoFromGallary() {

        //create an object of an Intent that picks files for you and spcify that it should pick images
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        //allows you to use your phone's camera to snap pitures
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(intent, CAMERA)
    }

    //After selecting an image from gallery or capturing photo from camera, an onActivityResult() method is executed.
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        // checks if we picked image from Gallery
        if (requestCode == GALLERY) {
            if (data != null) {
                //gets the image we picked
                val contentURI = data!!.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    //displays the image for us on our image view
                    picture_display!!.setImageBitmap(bitmap)

                }
                //catches erros if there is any
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        //checks if we snapped picture with camera
        else if (requestCode == CAMERA) {
            //gets the image we snapped with the camera
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            //displays the image on our image view
            picture_display!!.setImageBitmap(thumbnail)
            Toast.makeText(this, "Image uploaded!", Toast.LENGTH_SHORT).show()
        }


        /*val submmit: Button = findViewById(R.id.bt_signup_submit)

            submmit.setOnClickListener(View.OnClickListener {
                var enter =Intent(this,welcome::class.java)
                startActivity(enter)
            })*/

    }
    fun getBytes(bitmap: Bitmap):ByteArray{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
//        val valueImg = stream.toByteArray()
        return stream.toByteArray()
    }
}

