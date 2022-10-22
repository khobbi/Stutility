package com.edon.stutility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edon.stutility.databinding.ActivityTimetabelHelpBinding

class TimetableHelpActivity : AppCompatActivity() {
    lateinit var bnd: ActivityTimetabelHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityTimetabelHelpBinding.inflate(layoutInflater)
        setContentView(bnd.root)
    }
}