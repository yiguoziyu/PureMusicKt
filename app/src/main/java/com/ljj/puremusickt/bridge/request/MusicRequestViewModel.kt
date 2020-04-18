package com.ljj.puremusickt.bridge.request

import androidx.lifecycle.MutableLiveData
import com.ljj.puremusickt.data.bean.TestAlbum
import com.ljj.puremusickt.data.repository.HttpRequestManager
import com.ljj.puremusickt.ui.base.RequestViewModel

/**
 * 音乐资源  RequestViewModel
 */
class MusicRequestViewModel : RequestViewModel() {
    private val _freeMusicsLiveData = MutableLiveData<TestAlbum?>()
    val freeMusicsLiveData: MutableLiveData<TestAlbum?>
        get() = _freeMusicsLiveData

    fun requestFreeMusics() {
        HttpRequestManager.getInstance().getFreeMusic(_freeMusicsLiveData)
    }
}