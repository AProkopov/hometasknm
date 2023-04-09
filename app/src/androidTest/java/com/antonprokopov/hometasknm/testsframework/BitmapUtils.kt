package com.antonprokopov.hometasknm.testsframework

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.util.*

fun drawableToBitmap(drawable: Drawable): Bitmap? {
    var bitmap: Bitmap? = null

    if (drawable is BitmapDrawable) {
        if (drawable.bitmap != null) {
            return drawable.bitmap
        }
    }

    if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
        bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
    } else {
        bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    }

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
    drawable.draw(canvas)
    return bitmap
}

fun compareBitmaps(b1: Bitmap?, b2: Bitmap?): Boolean {
    if (b1 == null || b2 == null) {
        return false
    }
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1) {
        return b1.sameAs(b2)
    } else {
        if (b1.width == b2.width && b1.height == b2.height) {
            val pixels1 = IntArray(b1.width * b1.height)
            val pixels2 = IntArray(b2.width * b2.height)
            b1.getPixels(pixels1, 0, b1.width, 0, 0, b1.width, b1.height)
            b2.getPixels(pixels2, 0, b2.width, 0, 0, b2.width, b2.height)
            return Arrays.equals(pixels1, pixels2)
        } else {
            return false
        }
    }
}