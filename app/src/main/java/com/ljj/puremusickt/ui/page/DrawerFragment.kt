package com.ljj.puremusickt.ui.page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ljj.architecture.ui.adapter.SimpleBaseBindingAdapter
import com.ljj.puremusickt.R
import com.ljj.puremusickt.bridge.request.InfoRequestViewModel
import com.ljj.puremusickt.bridge.state.DrawerViewModel
import com.ljj.puremusickt.data.bean.LibraryInfo
import com.ljj.puremusickt.databinding.AdapterLibraryBinding
import com.ljj.puremusickt.databinding.FragmentDrawerBinding
import com.ljj.puremusickt.ui.base.BaseFragment

class DrawerFragment : BaseFragment() {
    private var mBinding: FragmentDrawerBinding? = null

    private val mDrawerViewModel: DrawerViewModel by viewModels()
    private val mInfoRequestViewModel: InfoRequestViewModel by viewModels()
    private var mAdapter: SimpleBaseBindingAdapter<LibraryInfo, AdapterLibraryBinding>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_drawer, container, false)
        mBinding = FragmentDrawerBinding.bind(view).apply {
            vm = mDrawerViewModel
            click = ClickProxy()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = object : SimpleBaseBindingAdapter<LibraryInfo, AdapterLibraryBinding>(
            requireContext(),
            R.layout.adapter_library
        ) {
            override fun onSimpleBindItem(
                binding: AdapterLibraryBinding?,
                item: LibraryInfo?,
                holder: RecyclerView.ViewHolder?
            ) {
                binding?.apply {
                    info = item
                    root.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item?.url ?: "")))
                    }
                }
            }

        }.also { mBinding?.rv?.adapter = it }

        mInfoRequestViewModel.apply {
            libraryLiveData.observe(viewLifecycleOwner, Observer { libraryInfos ->
                mInitDataCame = true
                if (mAnimationLoaded && libraryInfos != null) {
                    mAdapter?.apply {
                        list = libraryInfos
                        notifyDataSetChanged()
                    }
                }
            })
        }.apply {
            requestLibraryInfo()
        }
    }

    override fun loadInitData() {
        super.loadInitData()
        mInfoRequestViewModel.libraryLiveData.value?.also {
            mAdapter?.list = it
            mAdapter?.notifyDataSetChanged()
        }
    }

    inner class ClickProxy {
        fun logoClick() {
            val u = "https://github.com/KunMinX/Jetpack-MVVM-Best-Practice"
            val uri = Uri.parse(u)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

}