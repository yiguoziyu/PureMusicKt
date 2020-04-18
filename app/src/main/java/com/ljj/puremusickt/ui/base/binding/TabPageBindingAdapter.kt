package com.ljj.puremusickt.ui.base.binding

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ljj.architecture.ui.adapter.CommonViewPagerAdapter
import com.ljj.puremusickt.R

object TabPageBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["initTabAndPage"], requireAll = false)
    fun initTabAndPage(
        tabLayout: TabLayout,
        initTabAndPage: Boolean
    ) {
        if (initTabAndPage) {
            val count = tabLayout.tabCount
            val title = arrayOfNulls<String>(count)
            for (i in 0 until count) {
                val tab = tabLayout.getTabAt(i)
                if (tab != null && tab.text != null) {
                    title[i] = tab.text.toString()
                }
            }
            val viewPager: ViewPager = tabLayout.rootView.findViewById(R.id.view_pager)
            if (viewPager != null) {
                viewPager.adapter = CommonViewPagerAdapter(count, false, title)
                tabLayout.setupWithViewPager(viewPager)
            }
        }
    }
    @JvmStatic
    @BindingAdapter(value = ["tabSelectedListener"], requireAll = false)
    fun tabSelectedListener(
        tabLayout: TabLayout,
        listener: TabLayout.OnTabSelectedListener?
    ) {
        tabLayout.addOnTabSelectedListener(listener)
    }
}