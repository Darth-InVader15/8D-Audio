package com.cubesoft.musicx



import android.content.Context
import android.media.MediaPlayer
import android.media.ToneGenerator.MAX_VOLUME
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(var context: Context,var songs: ArrayList<SongInfo>) : RecyclerView.Adapter<SongAdapter.CustomAdapter>()
{


    var oldPosition: Int = -1
    var c: Int =1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter
    {
        var v: View = LayoutInflater.from(context).inflate(R.layout.custom_layout,parent,false)

        return CustomAdapter(v)

    }

    override fun getItemCount(): Int
    {
        return songs.size

    }

    override fun onBindViewHolder(holder: CustomAdapter, position: Int)
    {

        var title: String = songs[position].title
        var artist: String = songs[position].artist
        var count: Int = songs[position].count
        var albumArt: Uri = songs[position].albumArtUri
        var data: Uri = songs[position].dataUri
        holder.albumArt.setImageURI(albumArt)
        holder.Title.text = title
        holder.Artist.text = artist
        holder.count.text = count.toString()





//        var soundVolume = 0.0
//        var MAX_VOLUME: Float = 100F
//        var  volumeL: Float = (1 - (Math.log(MAX_VOLUME - soundVolume) / Math.log(MAX_VOLUME.toDouble()))).toFloat()


        holder.itemView.setOnClickListener {

            Toast.makeText(context,"Dumping the activity.. ",Toast.LENGTH_SHORT).show()



            Navigation.findNavController(holder.itemView).navigate(displayFragmentDirections.actionDisplayFragmentToPlayerFragment(data,albumArt,count,title,artist))





//            if(currentPosition!=oldPosition)
//            {
//                mediaPlayer.reset()
//                mediaPlayer.setDataSource(context,data)
//                mediaPlayer.prepare()
////                mediaPlayer.setVolume((100.0 - volumeL).toFloat(),volumeL.toFloat() )
//
//                holder.playButton.setImageResource(R.drawable.pause)
//                mediaPlayer.start()
//
//                oldPosition = currentPosition
//                c=1
//
//                do
//                {
//                    var soundVol: Int = (c%10)
//                    if((c/10)%2==0)
//                    {
//
//                        val volumeL : Float = ((c%10))/10.toFloat()
//                        mediaPlayer.setVolume((1-volumeL), volumeL)
//                    }
//
//                    else
//                    {
//
//
//                        val volumeL : Float = (c%100).toFloat()
//                        mediaPlayer.setVolume((volumeL), (1-volumeL))
//                    }
//                    c++
//
//
//                }while (mediaPlayer.isPlaying)
//
//
//            }
//            else
//            {
//                if (mediaPlayer.isPlaying)
//                {
//                    mediaPlayer.pause()
//                    holder.playButton.setImageResource(R.drawable.play)
//                }
//                else
//                {
//                    mediaPlayer.start()
//                    holder.playButton.setImageResource(R.drawable.pause)
//
//                }
//
//            }

        }
    }


    class CustomAdapter(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var Title: TextView = itemView.findViewById(R.id.textView_title)
        var Artist: TextView = itemView.findViewById(R.id.textView_artist)
        var albumArt: ImageView = itemView.findViewById(R.id.imageView_albumArt)

        var count: TextView = itemView.findViewById(R.id.textView_count)

    }

}