package com.antonprokopov.albumsfeed.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antonprokopov.albumsfeed.R

class AlbumsActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, AlbumsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            return intent
        }
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