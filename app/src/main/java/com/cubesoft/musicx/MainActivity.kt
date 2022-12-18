package com.cubesoft.musicx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity()
{
    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)


    }

    override fun onBackPressed()
    {

        super.onBackPressed()
        //Toast. makeText(this,"BACK",Toast.LENGTH_LONG).show()

    }


    fun settings(v:View)
    {
        Toast.makeText(this,"settings",Toast.LENGTH_SHORT).show()
        var intent : Intent = Intent(this,settings::class.java)
        startActivity(intent)

    }



}
