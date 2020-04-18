package com.ljj.puremusickt

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.ljj.architecture.utils.Utils
import com.ljj.puremusickt.player.PlayerManager

class App : Application(), ViewModelStoreOwner {
    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null
    companion object{
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
        mAppViewModelStore = ViewModelStore()
        Utils.init(this)
        PlayerManager.getInstance().init(this)
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    fun getAppViewModelProvider(activity: Activity): ViewModelProvider {
        val context = (activity.applicationContext as App)
        return ViewModelProvider(context, context.getAppFactory(activity))
    }
    private fun getAppFactory(activity: Activity): ViewModelProvider.Factory =
        mFactory
            ?: ViewModelProvider.AndroidViewModelFactory.getInstance(checkApplication(activity))

    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }
}