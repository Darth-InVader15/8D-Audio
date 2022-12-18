package com.cubesoft.musicx


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission

import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtechviral.mplaylib.MusicFinder
import kotlinx.android.synthetic.main.fragment_display.*
import com.cubesoft.musicx.databinding.FragmentDisplayBinding


class displayFragment : Fragment()
{

    var Songs: ArrayList<SongInfo> = ArrayList()
    lateinit var songAdapter: SongAdapter
    lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){

        }
        else{
            Toast.makeText(context,"Permission not granted. Shutting down.", Toast.LENGTH_LONG).show()
            activity?.finish()

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        var binding: FragmentDisplayBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_display,container,false)
        var v : View = binding.root
        var recyclerView:RecyclerView = v.findViewById(R.id.recyclerView)
        viewManager = LinearLayoutManager(context)




        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),0)
        }
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),0)

        }

        else{
            var musicFinder: MusicFinder = MusicFinder(activity!!.contentResolver)
            musicFinder.prepare()



            var songs:List<MusicFinder.Song> = musicFinder.allSongs
            var c : Int = 1
            for (song in songs)
            {
                Songs.add(SongInfo(c,song.title,song.artist,song.albumArt,song.uri))
                c++


            }

            songAdapter = SongAdapter(context!!,Songs)
            recyclerView.adapter = songAdapter
            recyclerView.layoutManager = viewManager

        }




        return v



    }





}
