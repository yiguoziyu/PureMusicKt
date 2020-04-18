package com.ljj.puremusickt.ui.base

import android.os.Bundle
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ljj.architecture.data.manager.NetState
import com.ljj.architecture.data.manager.NetworkStateManager
import com.ljj.puremusickt.App
import com.ljj.puremusickt.bridge.callback.SharedViewModel
import kotlinx.coroutines.*

abstract class BaseFragment : Fragment() {
    protected var mAnimationEnterLoaded = false
    protected var mAnimationLoaded = false
    protected var mInitDataCame = false
    lateinit var mSharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSharedViewModel = getAppViewModelProvider().get(SharedViewModel::class.java)
        NetworkStateManager.getInstance().mNetworkStateCallback.observe(this, Observer {
            onNetworkStateChanged(it)
        })
    }

    protected open fun onNetworkStateChanged(netState: NetState?) { //TODO 子类可以重写该方法，统一的网络状态通知和处理
    }

    open fun nav(): NavController {
        return findNavController()
    }

    open fun loadInitData() {}

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        GlobalScope.launch {
            delay(280)
           withContext(Dispatchers.Main){
               mAnimationLoaded = true
               if (mInitDataCame && !mAnimationEnterLoaded) {
                   mAnimationEnterLoaded = true
                   loadInitData()
               }
           }
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    protected open fun getAppViewModelProvider(): ViewModelProvider {
        return (requireActivity().applicationContext as App).getAppViewModelProvider(requireActivity())
    }
}