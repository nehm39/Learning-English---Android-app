package com.learning.english.simple.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.learning.english.simple.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(0, R.anim.fade_out)
        }, 1000)
    }
}
