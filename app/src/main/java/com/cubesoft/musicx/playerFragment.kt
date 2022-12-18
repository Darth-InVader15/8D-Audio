package com.cubesoft.musicx


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.databinding.DataBindingUtil
import com.cubesoft.musicx.databinding.FragmentPlayerBinding
import kotlinx.android.synthetic.main.fragment_player.*
import java.lang.Exception
import android.media.audiofx.Visualizer




class playerFragment : Fragment()
{

    var mediaPlayer: MediaPlayer = MediaPlayer()
    lateinit var seekBar: SeekBar
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()
    lateinit var currentTime: TextView
    lateinit var remainingTime: TextView
    lateinit var volL: TextView
    lateinit var volR : TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        var binding:FragmentPlayerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_player,container,false)
        var v : View = binding.root

        var albumArt : ImageView = v.findViewById(R.id.imageView_albumArtL)
        var play: ImageView = v.findViewById(R.id.button_playPause)
        var next :ImageView = v.findViewById(R.id.button_next)
        var previous: ImageView = v.findViewById(R.id.button_previous)
        seekBar = v.findViewById(R.id.seekBar)
        var Title: TextView = v.findViewById(R.id.textView_titleL)
        var Artist : TextView = v.findViewById(R.id.textView_artistL)
        currentTime = v.findViewById(R.id.currentTime)
        remainingTime = v.findViewById(R.id.remainingTime)
        volL = v.findViewById(R.id.volLeft)
        volR = v.findViewById(R.id.volRight)




        var args = playerFragmentArgs.fromBundle(arguments!!)

        albumArt.setImageURI(args.albumArtUri)
        var data: Uri = args.dataUri
        var title: String =args.title
        var artist:String = args.artist
        var count:Int = args.count
        




        mediaPlayer.reset()
        mediaPlayer.setDataSource(context!!,data)
        mediaPlayer.prepare()

        Title.text = title
        Artist.text = artist
        seekBar.max = mediaPlayer.duration



        mediaPlayer.start()


        initializeSeekBar()

        play.setOnClickListener {
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                play.setImageResource(R.drawable.pla)

            }
            else{
                mediaPlayer.start()
                play.setImageResource(R.drawable.pause)


            }
        }

        next.setOnClickListener {
            Toast.makeText(context,"Function will be available soon :)",Toast.LENGTH_SHORT).show()
        }
        previous.setOnClickListener {
            Toast.makeText(context,"Function will be available soon :)",Toast.LENGTH_SHORT).show()
        }



        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()

        }








        return v

    }



    private fun initializeSeekBar() {
        seekBar.max = mediaPlayer.duration
        var c : Int = 0
        var vol_left : ArrayList<Float> = ArrayList()

        vol_left.add(1.0F)
        vol_left.add(0.87F)
        vol_left.add(0.80F)
        vol_left.add(0.75F)
        vol_left.add(0.64F)
        vol_left.add(0.6F)
        vol_left.add(0.42F)
        vol_left.add(0.35F)
        vol_left.add(0.27F)
        vol_left.add(0.15F)
        
        
        var vol_right : ArrayList<Float> = ArrayList()

        vol_right.add(0.18F)
        vol_right.add(0.27F)
        vol_right.add(0.35F)
        vol_right.add(0.42F)
        vol_right.add(0.6F)
        vol_right.add(0.64F)
        vol_right.add(0.75F)
        vol_right.add(0.80F)
        vol_right.add(0.87F)
        vol_right.add(1.0F)
        
        
        
        
        try {


        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentPosition               //Update the seekBar
            currentTime.text = ((mediaPlayer.currentPosition)/1000).toInt().toString()
            remainingTime.text = ((mediaPlayer.duration-(mediaPlayer.currentPosition))/1000).toInt().toString()




            if((c/10)%2==0)
            {

//                val volumeL : Float = ((c%10))/10.toFloat()
                mediaPlayer.setVolume(vol_left[((c%10))],vol_right[(c%10)])
                volL.text = vol_left[((c%10))].toString()
                volR.text = vol_right[(c%10)].toString()
            }

            else
            {


//                val volumeL : Float = (c%100).toFloat()
                mediaPlayer.setVolume(vol_right.get((c%10)),vol_left.get(c%10))
                volR.text = vol_left[((c%10))].toString()
                volL.text = vol_right[(c%10)].toString()
            }
            c++

            handler.postDelayed(runnable, 350)
        }
        handler.postDelayed(runnable, 350)
        }
        catch (e:Exception)
        {
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy()
    {
        mediaPlayer.release()
        seekBar.progress =0
        seekBar = SeekBar(context)
        mediaPlayer = MediaPlayer()
        Toast.makeText(context,"destroyed",Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }



}

/**
 * A simple class that draws waveform data received from a
 * [Visualizer.OnDataCaptureListener.onWaveFormDataCapture]
 */
internal class VisualizerView(context: Context) : View(context) {
    private var mBytes: ByteArray? = null
    private var mPoints: FloatArray? = null
    private val mRect = Rect()

    private val mForePaint = Paint()

    init {
        init()
    }

    private fun init() {
        mBytes = null

        mForePaint.setStrokeWidth(1f)
        mForePaint.setAntiAlias(true)
        mForePaint.setColor(Color.rgb(0, 128, 255))
    }

    fun updateVisualizer(bytes: ByteArray) {
        mBytes = bytes
        invalidate()
    }

    override protected fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mBytes == null) {
            return
        }

        if (mPoints == null || mPoints!!.size < mBytes!!.size * 4) {
            mPoints = FloatArray(mBytes!!.size * 4)
        }

        mRect.set(0, 0, width, height)

        for (i in 0 until mBytes!!.size - 1) {
            mPoints!![i * 4] = (mRect.width() * i / (mBytes!!.size - 1)).toFloat()
            mPoints!![i * 4 + 1] = (mRect.height() / 2 + (mBytes!![i] + 128).toByte() * (mRect.height() / 2) / 128).toFloat()
            mPoints!![i * 4 + 2] = (mRect.width() * (i + 1) / (mBytes!!.size - 1)).toFloat()
            mPoints!![i * 4 + 3] = (mRect.height() / 2 + (mBytes!![i + 1] + 128).toByte() * (mRect.height() / 2) / 128).toFloat()
        }

        canvas.drawLines(mPoints!!, mForePaint)
    }
}



