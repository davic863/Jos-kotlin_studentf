package com.example.studentsforum.helpers

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsforum.R
import com.example.studentsforum.ViewUser
import com.example.studentsforum.model.Users
import java.sql.Blob

class usersRecyclerAdapter(private val listUsers:List<Users>,internal var context: Context) : RecyclerView.Adapter <usersRecyclerAdapter.UserviewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserviewHolder {
        //inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.users_list_card,parent,false)
        return  UserviewHolder(itemView)

    }

    override fun getItemCount(): Int {

        return listUsers.size

    }

    override fun onBindViewHolder(holder: UserviewHolder, position: Int) {

        holder.textName.text = listUsers[position].name
        holder.textEmail.text = listUsers[position].email
        holder.textAddress.text= listUsers[position].address
        val bmp = BitmapFactory.decodeByteArray(listUsers[position].image,0,listUsers[position].image.size)
        holder.imageUsers.setImageBitmap(bmp)


        //set onclick listner on a users data
        holder.itemView.setOnClickListener(View.OnClickListener {

            val  i = Intent(context,ViewUser::class.java)

            // pass the details of the user to the next activity

            i.putExtra("id", listUsers[position] .id)
            i.putExtra("name",listUsers[position].name)
            i.putExtra("email", listUsers[position].email)
            i.putExtra("address",listUsers[position].address)
            i.putExtra("password", listUsers[position].password)
            i.putExtra("image", listUsers[position].image)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        })


    }


    inner class UserviewHolder(view: View):RecyclerView.ViewHolder(view){
        var textName: TextView
        var textEmail: TextView
        var textAddress:TextView
        var imageUsers:ImageView


        init {
            textName = view.findViewById<View>(R.id.user_name) as TextView
            textEmail = view.findViewById(R.id.user_email) as TextView
            textAddress = view.findViewById(R.id.user_address) as TextView
            imageUsers  = view.findViewById(R.id.user_image) as ImageView

        }

    }
}