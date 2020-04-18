package com.ljj.puremusickt

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.ljj.puremusickt.bridge.state.MainActivityViewModel
import com.ljj.puremusickt.databinding.ActivityMainBinding
import com.ljj.puremusickt.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mMainActivityViewModel by viewModels<MainActivityViewModel>()
    private var isListened = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityViewModel.initState()

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.lifecycleOwner = this
        mBinding.vm = mMainActivityViewModel

        //用全局状态来保持页面viewmodel的持久性
        mSharedViewModel.apply {
            activityCanBeClosedDirectly.observe(this@MainActivity, Observer {
                val nav = Navigation.findNavController(this@MainActivity, R.id.main_fragment_host)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainFragment) {
                    nav.navigateUp()
                } else if (mBinding.dl.isDrawerOpen(GravityCompat.START)) {
                    mBinding.dl.closeDrawer(GravityCompat.START)
                } else {
                    super.onBackPressed()
                }
            })
        }.apply {
            openOrCloseDrawer.observe(this@MainActivity, Observer {
                mMainActivityViewModel.openDrawer.setValue(it)
            })
            enableSwipeDrawer.observe(this@MainActivity, Observer {
                mMainActivityViewModel.allowDrawerOpen.setValue(it)
            })
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!isListened) {
            mSharedViewModel.timeToAddSlideListener.value = true
            isListened = true
        }
    }

    override fun onBackPressed() {
        mSharedViewModel.closeSlidePanelIfExpanded.value = true
    }

}
