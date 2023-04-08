package com.antonprokopov.appstartup.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antonprokopov.appstartup.R
import com.antonprokopov.appstartup.di.AppStartupComponentHolder
import com.antonprokopov.core.ui.ActivityLifecycleOwnerHolder
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var activityLifecycleOwnerHolder: ActivityLifecycleOwnerHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppStartupComponentHolder.initComponent().inject(this)
        activityLifecycleOwnerHolder.init(this)

        setContentView(R.layout.activity_splash)

        val fragment = supportFragmentManager.findFragmentByTag(SplashFragment.TAG) ?: SplashFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, SplashFragment.TAG)
        transaction.commit()
    }

    override fun onDestroy() {
        AppStartupComponentHolder.releaseComponent()
        super.onDestroy()
    }
}