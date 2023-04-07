package com.antonprokopov.appstartup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.antonprokopov.appstartup.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val fragment = supportFragmentManager.findFragmentByTag(SplashFragment.TAG) ?: SplashFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, SplashFragment.TAG)
        transaction.commit()
    }
}