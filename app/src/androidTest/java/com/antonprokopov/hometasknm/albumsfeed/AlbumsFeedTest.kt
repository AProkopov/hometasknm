package com.antonprokopov.hometasknm.albumsfeed

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.antonprokopov.appstartup.ui.SplashActivity
import com.antonprokopov.hometasknm.testsframework.CountingIdlingResourceSingleton
import com.antonprokopov.hometasknm.testsframework.I
import com.antonprokopov.hometasknm.testsframework.dontSee
import com.antonprokopov.hometasknm.testsframework.see
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumsFeedTest {

    @get:Rule
    val startActivity = ActivityScenarioRule(SplashActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @Test
    fun useAppContext() {
        I see "Albums"
        I dontSee  "Photo albums"
        I dontSee  "Albums list is empty"
        I dontSee  "Unfortunately an error occurred"
    }
}