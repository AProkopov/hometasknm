package com.antonprokopov.core.ui

import androidx.viewbinding.ViewBinding

abstract class ViewBindingUi<T: ViewBinding> {
    var fragmentViewBinding: T? = null
}