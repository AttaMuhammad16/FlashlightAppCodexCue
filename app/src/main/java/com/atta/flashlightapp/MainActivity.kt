package com.atta.flashlightapp

import android.annotation.SuppressLint
import android.app.Activity
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.black)

        val img=findViewById<ImageView>(R.id.img)

        var i=true
        val vibrator= getSystemService(VIBRATOR_SERVICE) as Vibrator

        img.setOnClickListener {
            if (i){
                vibratePhone(vibrator,60)
                onFlash(this)
                img.setImageResource(R.drawable.flash_light_on)
                i=false
            }else{
                vibratePhone(vibrator,60)
                offFlash(this)
                img.setImageResource(R.drawable.flash_light_of)
                i=true
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onFlash(context: Activity){
        val cameraManager : CameraManager =context.getSystemService(AppCompatActivity.CAMERA_SERVICE) as CameraManager
        try{
            var cameraId : String? = null
            cameraId = cameraManager.cameraIdList[0]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager?.setTorchMode(cameraId,true)
            }
        }catch (e: CameraAccessException){
            Toast.makeText(context, "Something wrong", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    fun offFlash(context:Activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val cameraManage = context.getSystemService(AppCompatActivity.CAMERA_SERVICE) as CameraManager
            try {
                val cameraId = cameraManage.cameraIdList[0]
                cameraManage?.setTorchMode(cameraId,false)
            }catch (e: CameraAccessException){
                Toast.makeText(context, "Something wrong", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun vibratePhone(vibrator:Vibrator,milliseconds:Long){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(milliseconds)
        }
    }



}