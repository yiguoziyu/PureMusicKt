package com.ljj.puremusickt.bridge.request

import androidx.lifecycle.MutableLiveData
import com.ljj.puremusickt.data.bean.LibraryInfo
import com.ljj.puremusickt.data.repository.HttpRequestManager
import com.ljj.puremusickt.ui.base.RequestViewModel

/**
 * 信息列表 RequestViewModel
 */
class InfoRequestViewModel : RequestViewModel() {
    private val _libraryLiveData = MutableLiveData<List<LibraryInfo?>?>()

    val libraryLiveData: MutableLiveData<List<LibraryInfo?>?>
        get() = _libraryLiveData

    fun requestLibraryInfo() {
        HttpRequestManager.getInstance().getLibraryInfo(_libraryLiveData)
    }
}