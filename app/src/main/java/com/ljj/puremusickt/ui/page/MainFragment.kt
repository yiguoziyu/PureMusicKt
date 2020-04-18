package com.ljj.puremusickt.ui.page

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ljj.architecture.ui.adapter.SimpleBaseBindingAdapter
import com.ljj.puremusickt.R
import com.ljj.puremusickt.bridge.request.MusicRequestViewModel
import com.ljj.puremusickt.bridge.state.MainViewModel
import com.ljj.puremusickt.data.bean.TestAlbum
import com.ljj.puremusickt.databinding.AdapterPlayItemBinding
import com.ljj.puremusickt.databinding.FragmentMainBinding
import com.ljj.puremusickt.player.PlayerManager
import com.ljj.puremusickt.ui.base.BaseFragment
import com.ljj.puremusickt.ui.helper.S_HELPER

class MainFragment : BaseFragment() {

    private val mMainViewModel by viewModels<MainViewModel>()
    private val mMusicRequestViewModel by viewModels<MusicRequestViewModel>()
    private lateinit var mBinding: FragmentMainBinding
    private var mAdapter: SimpleBaseBindingAdapter<TestAlbum.TestMusic, AdapterPlayItemBinding>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        mBinding = FragmentMainBinding.bind(view).apply {
            click = ClickProxy()
            vm = mMainViewModel
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMainViewModel.apply {
            initTabAndPage.set(true)
            pageAssetPath.set("summary.html")
        }
        mAdapter = object : SimpleBaseBindingAdapter<TestAlbum.TestMusic, AdapterPlayItemBinding>(
            requireContext(),
            R.layout.adapter_play_item
        ) {
            override fun onSimpleBindItem(
                binding: AdapterPlayItemBinding?,
                item: TestAlbum.TestMusic?,
                holder: RecyclerView.ViewHolder?
            ) {
                binding?.let {
                    it.tvTitle.text = item?.title ?: ""
                    it.tvArtist.text = item?.artist?.name ?: ""
                    Glide.with(it.ivCover.context).load(item?.coverImg ?: "").into(it.ivCover)
                    val currentIndex = PlayerManager.getInstance().albumIndex
                    it.ivPlayStatus.setColor(
                        if (currentIndex == holder?.adapterPosition) resources.getColor(R.color.gray) else Color.TRANSPARENT
                    )
                    it.rootView.setOnClickListener { view ->
                        holder?.adapterPosition?.also { pos ->
                            PlayerManager.getInstance().playAudio(pos)
                        }
                    }

                }
            }

        }
        mBinding.rv.adapter = mAdapter


        PlayerManager.getInstance().changeMusicLiveData.observe(viewLifecycleOwner, Observer {
            mAdapter?.notifyDataSetChanged()
        })

        mMusicRequestViewModel.freeMusicsLiveData.observe(viewLifecycleOwner, Observer {musicAlbum->
            musicAlbum?.musics?.let {
                mAdapter?.list=it
                mAdapter?.notifyDataSetChanged()
                PlayerManager.getInstance().apply {
                    if (album==null||album.albumId != musicAlbum.albumId){
                        loadAlbum(musicAlbum)
                    }
                }
            }

        })

        if (PlayerManager.getInstance().album == null) {
            mMusicRequestViewModel.requestFreeMusics()
        } else {
            mAdapter?.list = PlayerManager.getInstance().album.musics
            mAdapter?.notifyDataSetChanged()
        }

        S_HELPER.openDrawer.observe(viewLifecycleOwner, Observer {
            mSharedViewModel.openOrCloseDrawer.value = true
        })

    }


    inner class ClickProxy {
        fun openMenu() {
            mSharedViewModel.openOrCloseDrawer.value = true
        }

        fun search() {
            nav().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }
}