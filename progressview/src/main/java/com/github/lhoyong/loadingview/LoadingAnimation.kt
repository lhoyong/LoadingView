package com.github.lhoyong.loadingview

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator

internal enum class LoadingAnimation(val type: Int) {
    NONE(0),
    BOUNCE(1),
    DECELERATE(2),
    ACCELERATE(3),
    ACCELERATEDECELERATE(4);

    fun getInterpolator(): Interpolator? {
        return when (type) {
            BOUNCE.type -> BounceInterpolator()
            DECELERATE.type -> DecelerateInterpolator()
            ACCELERATE.type -> AccelerateInterpolator()
            ACCELERATEDECELERATE.type -> AccelerateDecelerateInterpolator()
            else -> null
        }
    }
}
