package com.antonprokopov.albumsfeed.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antonprokopov.albumsfeed.R

class AlbumsActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context) =
            Intent(context, AlbumsActivity::class.java)
//                .putExtra(INITIAL_PHOTO_COUNT_KEY, initialPhotoCount)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_albums_feed)

        val fragment = supportFragmentManager.findFragmentByTag(AlbumsFragment.TAG) ?: AlbumsFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_feed_container, fragment, AlbumsFragment.TAG)
        transaction.commit()
    }
}