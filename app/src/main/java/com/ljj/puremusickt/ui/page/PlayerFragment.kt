package com.ljj.puremusickt.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kunminx.player.PlayingInfoManager
import com.ljj.puremusickt.R
import com.ljj.puremusickt.bridge.callback.SharedViewModel
import com.ljj.puremusickt.bridge.state.PlayerViewModel
import com.ljj.puremusickt.databinding.FragmentPlayerBinding
import com.ljj.puremusickt.player.PlayerManager
import com.ljj.puremusickt.ui.base.BaseFragment
import com.ljj.puremusickt.ui.view.PlayerSlideListener
import com.ljj.puremusickt.util.showToast
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder

class PlayerFragment : BaseFragment() {

    private val mPlayerViewModel: PlayerViewModel by viewModels()
    private lateinit var mBinding: FragmentPlayerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        mBinding = FragmentPlayerBinding.bind(view).apply {
            click = ClickProxy()
            event = EventHandler()
            vm = mPlayerViewModel
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSharedViewModel.apply {
            timeToAddSlideListener.observe(viewLifecycleOwner, Observer {
                getSlidingView(view)?.apply {
                    addPanelSlideListener(PlayerSlideListener(mBinding, this))
                    addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
                        override fun onPanelSlide(panel: View?, slideOffset: Float) {
                        }

                        override fun onPanelStateChanged(
                            panel: View?,
                            previousState: SlidingUpPanelLayout.PanelState?,
                            newState: SlidingUpPanelLayout.PanelState?
                        ) {
                            if (previousState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                                SharedViewModel.TAG_OF_SECONDARY_PAGES.add(this.javaClass.simpleName)
                            } else {
                                SharedViewModel.TAG_OF_SECONDARY_PAGES.remove(this.javaClass.simpleName)
                            }
                            mSharedViewModel.enableSwipeDrawer.value =
                                SharedViewModel.TAG_OF_SECONDARY_PAGES.size == 0
                        }

                    })
                }
            })
        }.apply {
            // slide状态观察
            closeSlidePanelIfExpanded.observe(viewLifecycleOwner, Observer {
                getSlidingView(view).let {
                    if (it != null) {
                        if (it.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                            it.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                        } else {
                            mSharedViewModel.activityCanBeClosedDirectly.setValue(true)
                        }
                    } else {
                        mSharedViewModel.activityCanBeClosedDirectly.setValue(true)
                    }
                }

            })
        }


        PlayerManager.getInstance().apply {
            // 切歌时，音乐的标题、作者、封面 状态的改变
            changeMusicLiveData.observe(viewLifecycleOwner, Observer { changeMusic ->
                mPlayerViewModel.apply {
                    title.set(changeMusic.title)
                    artist.set(changeMusic.summary)
                    coverImg.set(changeMusic.img)
                }
            })
        }.apply {
            // 播放进度 状态的改变
            playingMusicLiveData.observe(viewLifecycleOwner, Observer { playingMusic ->
                mPlayerViewModel.apply {
                    maxSeekDuration.set(playingMusic.duration)
                    currentSeekPosition.set(playingMusic.playerPosition)
                }
            })
        }.apply {
            // 播放按钮 状态的改变
            pauseLiveData.observe(viewLifecycleOwner, Observer { isPlay ->
                mPlayerViewModel.isPlaying.set(!isPlay)
            })
        }.apply {
            playModeLiveData.observe(viewLifecycleOwner, Observer { enum ->
                val tip = when (enum) {
                    PlayingInfoManager.RepeatMode.LIST_LOOP -> {
                        mPlayerViewModel.playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT)
                        R.string.play_repeat
                    }
                    PlayingInfoManager.RepeatMode.ONE_LOOP -> {
                        mPlayerViewModel.playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT_ONCE)
                        R.string.play_repeat_once
                    }
                    else -> {
                        mPlayerViewModel.playModeIcon.set(MaterialDrawableBuilder.IconValue.SHUFFLE)
                        R.string.play_shuffle
                    }
                }
                if (getSlidingView(view)?.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    tip.showToast()
                }
            })
        }
    }

    private fun getSlidingView(view: View): SlidingUpPanelLayout? {
        if (view.parent.parent is SlidingUpPanelLayout) {
            return view.parent.parent as SlidingUpPanelLayout
        }
        return null
    }

    inner class ClickProxy {
        fun playMode() {
            PlayerManager.getInstance().changeMode()
        }

        fun previous() {
            PlayerManager.getInstance().playPrevious()
        }

        fun togglePlay() {
            PlayerManager.getInstance().togglePlay()
        }

        fun next() {
            PlayerManager.getInstance().playNext()
        }

        fun showPlayList() {
            R.string.unfinished.showToast()
        }

        fun slideDown() {
            mSharedViewModel.closeSlidePanelIfExpanded.value = true
        }

        fun more() {}
    }

    inner class EventHandler : OnSeekBarChangeListener {
        override fun onProgressChanged(
            seekBar: SeekBar,
            progress: Int,
            fromUser: Boolean
        ) {
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {
            PlayerManager.getInstance().setSeek(seekBar.progress)
        }
    }
}