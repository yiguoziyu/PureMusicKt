package com.ljj.puremusickt.util

import android.widget.Toast
import com.ljj.puremusickt.App


fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.context, this, duration).show()
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.context, this, duration).show()
}

