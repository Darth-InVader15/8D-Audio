package com.cubesoft.musicx


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


class infoFragment : Fragment()
{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v: View =  inflater.inflate(R.layout.fragment_info, container, false)

        var skip: Button = v.findViewById(R.id.button_skipInfo)

        skip.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.displayFragment)
        }



        return v
    }


}
