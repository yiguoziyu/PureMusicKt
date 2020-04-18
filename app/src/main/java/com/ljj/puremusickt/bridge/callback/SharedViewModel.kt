package com.ljj.puremusickt.bridge.callback

import androidx.lifecycle.ViewModel
import com.ljj.architecture.bridge.callback.UnPeekLiveData

/***
 *
 * 专门控制UI状态的ViewModel
 */
class SharedViewModel : ViewModel() {

    companion object{
        @JvmStatic
        val TAG_OF_SECONDARY_PAGES= mutableListOf<String>()
    }
    val timeToAddSlideListener: UnPeekLiveData<Boolean> by lazy { UnPeekLiveData<Boolean>() }
    val closeSlidePanelIfExpanded: UnPeekLiveData<Boolean> by lazy { UnPeekLiveData<Boolean>() }
    val activityCanBeClosedDirectly: UnPeekLiveData<Boolean> by lazy { UnPeekLiveData<Boolean>() }
    val openOrCloseDrawer: UnPeekLiveData<Boolean> by lazy { UnPeekLiveData<Boolean>() }
    val enableSwipeDrawer: UnPeekLiveData<Boolean> by lazy { UnPeekLiveData<Boolean>() }
}