package com.ljj.puremusickt.bridge.request

import androidx.lifecycle.MutableLiveData
import com.ljj.architecture.data.usecase.UseCase
import com.ljj.architecture.data.usecase.UseCaseHandler
import com.ljj.puremusickt.data.bean.DownloadFile
import com.ljj.puremusickt.data.repository.HttpRequestManager
import com.ljj.puremusickt.data.usecase.CanBeStoppedUseCase
import com.ljj.puremusickt.ui.base.RequestViewModel

class DownloadViewModel : RequestViewModel() {
    private val _downloadFileLiveData = MutableLiveData<DownloadFile?>()
    private val _downloadFileCanBeStoppedLiveData = MutableLiveData<DownloadFile?>()
    private val _mCanBeStoppedUseCase = CanBeStoppedUseCase()

    val downloadFileLiveData: MutableLiveData<DownloadFile?>
        get() = _downloadFileLiveData
    val downloadFileCanBeStoppedLiveData: MutableLiveData<DownloadFile?>
        get() = _downloadFileCanBeStoppedLiveData
    val mCanBeStoppedUseCase: CanBeStoppedUseCase
        get() = _mCanBeStoppedUseCase


    fun requestDownloadFile() {
        HttpRequestManager.getInstance().downloadFile(_downloadFileLiveData)
    }

    fun requestCanBeStoppedDownloadFile() {
        UseCaseHandler.getInstance().execute(_mCanBeStoppedUseCase,
            CanBeStoppedUseCase.RequestValues(_downloadFileCanBeStoppedLiveData),
            object : UseCase.UseCaseCallback<CanBeStoppedUseCase.ResponseValue?> {
                override fun onSuccess(response: CanBeStoppedUseCase.ResponseValue?) {
                    _downloadFileCanBeStoppedLiveData.value = response?.liveData?.value
                }
                override fun onError() {}
            })
    }
}