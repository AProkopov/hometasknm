package com.antonprokopov.core.ui

import androidx.viewbinding.ViewBinding

abstract class ViewBindingUi<T: ViewBinding> {

    private var fragmentViewBinding: T? = null

    fun setViewBinding(fragmentViewBinding: T) {
        this.fragmentViewBinding = fragmentViewBinding
    }

}