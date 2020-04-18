package com.ljj.puremusickt.ui.helper

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.ljj.architecture.bridge.callback.UnPeekLiveData
import com.ljj.puremusickt.bridge.callback.SharedViewModel
import com.ljj.puremusickt.ui.base.BaseFragment

val S_HELPER = DrawerCoordinateHelper()

class DrawerCoordinateHelper : DefaultLifecycleObserver {
    val openDrawer: UnPeekLiveData<Boolean> = UnPeekLiveData()
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        SharedViewModel.TAG_OF_SECONDARY_PAGES.add(owner.javaClass.simpleName)
        (owner as BaseFragment).mSharedViewModel.enableSwipeDrawer.value =
            SharedViewModel.TAG_OF_SECONDARY_PAGES.size == 0
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        SharedViewModel.TAG_OF_SECONDARY_PAGES.remove(owner.javaClass.simpleName)
        (owner as BaseFragment).mSharedViewModel.enableSwipeDrawer.value =
            SharedViewModel.TAG_OF_SECONDARY_PAGES.size == 0
    }
}