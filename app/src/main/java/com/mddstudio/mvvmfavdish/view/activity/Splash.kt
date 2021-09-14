package com.mddstudio.mvvmfavdish.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import com.mddstudio.mvvmfavdish.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splash.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },1000)

    }
}