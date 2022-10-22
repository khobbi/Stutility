package com.edon.stutility

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import com.edon.stutility.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var bnd: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        val innerCircle = findViewById<LinearLayout>(R.id.splashInner)
        innerCircle.elevation = 5f

        val timer = 3000
        val intent = Intent(this, MainActivity::class.java)

        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, timer.toLong())
    }
}