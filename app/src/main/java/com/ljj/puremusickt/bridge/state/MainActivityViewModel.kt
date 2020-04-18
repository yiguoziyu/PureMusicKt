package com.ljj.puremusickt.bridge.state

import androidx.lifecycle.MutableLiveData
import com.ljj.puremusickt.ui.base.UiViewModel

class MainActivityViewModel : UiViewModel() {

    val openDrawer: MutableLiveData<Boolean> = MutableLiveData()

    val allowDrawerOpen: MutableLiveData<Boolean> = MutableLiveData()

    fun initState() {
        allowDrawerOpen.value = true
        openDrawer.value = false
    }
}