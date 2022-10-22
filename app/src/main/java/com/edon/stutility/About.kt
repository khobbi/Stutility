package com.edon.stutility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edon.stutility.databinding.ActivityAboutBinding

class About : AppCompatActivity() {
    lateinit var bnd: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(bnd.root)
    }
}