package com.antonprokopov.core.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner

class ActivityLifecycleOwnerHolder {

    lateinit var viewModelStoreOwner: ViewModelStoreOwner
    lateinit var lifecycleOwner: LifecycleOwner

    fun init(activity: AppCompatActivity) {
        viewModelStoreOwner = activity
        lifecycleOwner = activity
    }

}