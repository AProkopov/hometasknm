package com.antonprokopov.albumsfeed.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antonprokopov.albumsfeed.R
import com.antonprokopov.albumsfeed.di.AlbumsFeedComponentHolder
import com.antonprokopov.core.ui.ActivityLifecycleOwnerHolder
import javax.inject.Inject

class AlbumsActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, AlbumsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            return intent
        }
    }

    @Inject
    lateinit var activityLifecycleOwnerHolder: ActivityLifecycleOwnerHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AlbumsFeedComponentHolder.initComponent().inject(this)
        activityLifecycleOwnerHolder.init(this)

        setContentView(R.layout.activity_albums_feed)

        val fragment = supportFragmentManager.findFragmentByTag(AlbumsFragment.TAG) ?: AlbumsFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_feed_container, fragment, AlbumsFragment.TAG)
        transaction.commit()
    }

    override fun onDestroy() {
        AlbumsFeedComponentHolder.releaseComponent()
        super.onDestroy()
    }
}