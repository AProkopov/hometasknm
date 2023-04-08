package com.antonprokopov.core.utils

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object PicassoHelper {

    private val picasso: Picasso by lazy { Picasso.get() }

    fun loadImageSimply(
        view: ImageView,
        url: String,
        callback: Callback? = null
    ) {
        if (url.isNotEmpty()) {
            picasso
                .load(url)
                .into(view, callback)
        }
    }
}