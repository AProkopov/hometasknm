package com.antonprokopov.appstartup.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonprokopov.appstartup.databinding.FragmentSplashBinding
import com.antonprokopov.appstartup.di.AppStartupComponentHolder
import com.antonprokopov.core.ui.BaseViewBindingFragment
import javax.inject.Inject

class SplashFragment : BaseViewBindingFragment<FragmentSplashBinding>() {

    companion object {
        const val TAG = "SplashFragment"
    }

    @Inject
    lateinit var ui: SplashUI

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachedToRoot: Boolean,
        savedInstanceState: Bundle?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, attachedToRoot)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).also { ui.fragmentViewBinding = this.viewBinding }
    }

    override fun onAttach(context: Context) {
        AppStartupComponentHolder.initComponent().inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        AppStartupComponentHolder.releaseComponent()
        super.onDetach()
    }
}