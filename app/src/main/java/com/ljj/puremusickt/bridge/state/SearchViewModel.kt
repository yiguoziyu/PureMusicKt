package com.ljj.puremusickt.bridge.state

import androidx.databinding.ObservableField
import com.ljj.puremusickt.ui.base.UiViewModel

class SearchViewModel: UiViewModel() {
    val progress = ObservableField<Int>()
}