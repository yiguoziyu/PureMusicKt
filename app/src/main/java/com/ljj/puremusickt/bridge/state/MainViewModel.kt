package com.ljj.puremusickt.bridge.state

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.ljj.puremusickt.ui.base.UiViewModel

class MainViewModel : UiViewModel() {

    val initTabAndPage: ObservableBoolean = ObservableBoolean()

    val pageAssetPath: ObservableField<String> = ObservableField()
}