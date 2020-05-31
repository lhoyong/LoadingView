package com.github.lhoyong.loadingview

import android.animation.Animator
import android.animation.Animator.AnimatorListener

internal fun Animator.doRepeat(
    repeat: () -> Unit
) {
    addListener(object : AnimatorListener {
        override fun onAnimationStart(animator: Animator?) = Unit
        override fun onAnimationEnd(animator: Animator?) = Unit
        override fun onAnimationCancel(animator: Animator?) = Unit
        override fun onAnimationRepeat(animator: Animator?) = repeat()
    })
}
