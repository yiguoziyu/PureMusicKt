package com.ljj.puremusickt.ui.base.binding

import androidx.core.view.GravityCompat
import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout

@BindingAdapter(value = ["isOpenDrawer"], requireAll = false)
fun openDrawer(drawerLayout: DrawerLayout, isOpenDrawer: Boolean) {
    if (isOpenDrawer && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
        drawerLayout.openDrawer(GravityCompat.START)
    } else {
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}

@BindingAdapter(value = ["allowDrawerOpen"], requireAll = false)
fun allowDrawerOpen(drawerLayout: DrawerLayout, allowDrawerOpen: Boolean) {
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
}