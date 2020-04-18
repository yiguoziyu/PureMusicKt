package com.ljj.puremusickt.ui.page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ljj.puremusickt.R
import com.ljj.puremusickt.bridge.request.DownloadViewModel
import com.ljj.puremusickt.bridge.state.SearchViewModel
import com.ljj.puremusickt.databinding.FragmentSearchBinding
import com.ljj.puremusickt.ui.base.BaseFragment
import com.ljj.puremusickt.ui.helper.S_HELPER

class SearchFragment : BaseFragment() {

    private lateinit var mBinding: FragmentSearchBinding
    private val mSearchViewModel: SearchViewModel by viewModels()
    private val mDownloadViewModel: DownloadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(S_HELPER)
        lifecycle.addObserver(mDownloadViewModel.mCanBeStoppedUseCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        mBinding = FragmentSearchBinding.bind(view).apply {
            click = ClickProxy()
            vm = mSearchViewModel
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDownloadViewModel.apply {
            downloadFileLiveData.observe(viewLifecycleOwner, Observer {downloadFile->
                mSearchViewModel.progress.set(downloadFile?.progress)
            })
            downloadFileCanBeStoppedLiveData.observe(viewLifecycleOwner, Observer {downloadFile->
                mSearchViewModel.progress.set(downloadFile?.progress)
            })
        }
    }
    inner class ClickProxy {
        fun back() {
            nav().navigateUp()
        }

        fun testNav() {
            val u = "https://xiaozhuanlan.com/topic/5860149732"
            val uri = Uri.parse(u)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        fun subscribe() {
            val u = "https://xiaozhuanlan.com/kunminx"
            val uri = Uri.parse(u)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        fun testDownload() {
            mDownloadViewModel.requestDownloadFile()
        }

        fun testLifecycleDownload() {
            mDownloadViewModel.requestCanBeStoppedDownloadFile()
        }
    }
}