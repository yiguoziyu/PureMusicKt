package com.ljj.puremusickt.ui.base.binding

import androidx.databinding.BindingAdapter
import com.ljj.puremusickt.ui.view.PlayPauseView
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder.IconValue
import net.steamcrafted.materialiconlib.MaterialIconView

object IconBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["isPlaying"], requireAll = false)
    fun isPlaying(pauseView: PlayPauseView, isPlaying: Boolean) {
        if (isPlaying) {
            pauseView.play()
        } else {
            pauseView.pause()
        }
    }
    @JvmStatic
    @BindingAdapter(value = ["mdIcon"], requireAll = false)
    fun setIcon(view: MaterialIconView, iconValue: IconValue?) {
        view.setIcon(iconValue)
    }
}