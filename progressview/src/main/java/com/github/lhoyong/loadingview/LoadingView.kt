package com.github.lhoyong.loadingview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import androidx.annotation.ColorInt

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    @ColorInt
    var progressColor: Int = Color.BLACK

    @ColorInt
    var progressBackgroundColor: Int = Color.TRANSPARENT

    private var duration: Long = ANIM_DURATION

    private var progressPreviousX: Float = 0F
    private var progressX: Float = 0F

    private var progressWidth: Float = 100F

    private var radius: Float = RADIUS.dp

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.LoadingView, 0, 0)

        this.progressColor = attr.getColor(R.styleable.LoadingView_loading_color, progressColor)
        this.progressBackgroundColor = attr.getColor(
            R.styleable.LoadingView_loading_background_color,
            progressBackgroundColor
        )
        this.duration =
            attr.getInt(R.styleable.LoadingView_loading_duration, duration.toInt()).toLong()
        this.progressWidth =
            attr.getFloat(R.styleable.LoadingView_loading_indicator_width, progressWidth)
        this.radius = attr.getInt(R.styleable.LoadingView_loading_radius, RADIUS).toFloat()

        attr.recycle()

        autoAnimated()
    }

    override fun onDraw(canvas: Canvas?) {
        drawBackground(canvas)
        drawProgress(canvas)
        super.onDraw(canvas)
    }

    private fun drawBackground(canvas: Canvas?) {
        val paint = Paint()
        paint.color = progressBackgroundColor
        canvas?.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
    }

    private fun drawProgress(canvas: Canvas?) {
        val paint = Paint()
        paint.color = progressColor
        canvas?.drawRoundRect(
            progressPreviousX, 0f, progressX, height.toFloat(), radius, radius, paint
        )
    }

    private fun autoAnimated() {
        ValueAnimator.ofFloat(0f, 1f)
            .apply {
                duration = this@LoadingView.duration
                repeatCount = Animation.INFINITE
                addUpdateListener {
                    val progress = it.animatedValue as Float
                    progressPreviousX = width * progress
                    progressX =
                        if (((width.toFloat() * progress) + progressWidth) >= width.toFloat()) width.toFloat() else (width.toFloat() * progress) + progressWidth
                    invalidate()
                }
                start()
            }
    }

    companion object {
        private const val ANIM_DURATION = 3000L
        private const val RADIUS = 5
    }
}
