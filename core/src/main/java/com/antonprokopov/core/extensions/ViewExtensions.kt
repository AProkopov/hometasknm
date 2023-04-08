package com.antonprokopov.core.extensions

import android.view.View

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.setVisibleOrGone(value: Boolean) {
    if (value) visible() else gone()
}