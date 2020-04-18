package com.ljj.puremusickt.ui.base

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ljj.architecture.data.manager.NetworkStateManager
import com.ljj.architecture.utils.AdaptScreenUtils
import com.ljj.architecture.utils.BarUtils
import com.ljj.architecture.utils.ScreenUtils
import com.ljj.puremusickt.App
import com.ljj.puremusickt.bridge.callback.SharedViewModel

abstract class BaseActivity : AppCompatActivity() {

     lateinit var mSharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        BarUtils.setStatusBarLightMode(this, true)

        mSharedViewModel = getAppViewModelProvider().get(SharedViewModel::class.java)

        lifecycle.addObserver(NetworkStateManager.getInstance())
    }

    override fun getResources(): Resources? {
        return if (ScreenUtils.isPortrait()) {
            AdaptScreenUtils.adaptWidth(super.getResources(), 360)
        } else {
            AdaptScreenUtils.adaptHeight(super.getResources(), 640)
        }
    }

    protected open fun getAppViewModelProvider(): ViewModelProvider {
        return (applicationContext as App).getAppViewModelProvider(this)
    }
}