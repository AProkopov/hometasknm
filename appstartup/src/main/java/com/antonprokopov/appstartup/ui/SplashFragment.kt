package com.antonprokopov.appstartup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.antonprokopov.appstartup.databinding.FragmentSplashBinding
import com.antonprokopov.core.ui.BaseViewBindingFragment

class SplashFragment : BaseViewBindingFragment<FragmentSplashBinding>() {

    companion object {
        const val TAG = "SplashFragment"
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachedToRoot: Boolean,
        savedInstanceState: Bundle?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, attachedToRoot)
    }
}