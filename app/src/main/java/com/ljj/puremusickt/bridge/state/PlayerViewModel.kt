package com.ljj.puremusickt.bridge.state

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.kunminx.player.PlayingInfoManager
import com.ljj.architecture.utils.Utils
import com.ljj.puremusickt.R
import com.ljj.puremusickt.player.PlayerManager
import com.ljj.puremusickt.ui.base.UiViewModel
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder.IconValue

class PlayerViewModel : UiViewModel() {
    val title = ObservableField<String>()

    val artist = ObservableField<String>()

    val coverImg = ObservableField<String>()

    val placeHolder = ObservableField<Drawable>()

    val maxSeekDuration = ObservableInt()

    val currentSeekPosition = ObservableInt()

    val isPlaying = ObservableBoolean()

    val playModeIcon = ObservableField<IconValue>()

    init {
        title.set(Utils.getApp().getString(R.string.app_name))
        artist.set(Utils.getApp().getString(R.string.app_name))
        placeHolder.set(Utils.getApp().resources.getDrawable(R.drawable.bg_album_default))

        playModeIcon.set(
            when (PlayerManager.getInstance().repeatMode) {
                PlayingInfoManager.RepeatMode.LIST_LOOP -> IconValue.REPEAT
                PlayingInfoManager.RepeatMode.ONE_LOOP -> IconValue.REPEAT_ONCE
                else -> IconValue.SHUFFLE
            }
        )

    }
}