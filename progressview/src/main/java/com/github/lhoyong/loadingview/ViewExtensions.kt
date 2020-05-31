package com.github.lhoyong.loadingview

import android.content.res.Resources

val Int.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)
