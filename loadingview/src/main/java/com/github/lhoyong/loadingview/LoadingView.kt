package com.github.lhoyong.loadingview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import androidx.annotation.ColorInt

/** Horizontal ProgressBar with endless Animation **/
class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /** progressBar Color */
    @ColorInt
    var progressColor: Int = Color.BLACK
        set(value) {
            field = value
            updateView()
        }

    /** backgroundColor */
    @ColorInt
    var progressBackgroundColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            updateView()
        }

    /**
     * progress duration
     * default is [LoadingView.ANIM_DURATION]
     */
    var duration: Long = ANIM_DURATION
        set(value) {
            field = value
            updateView()
        }

    /** progressIndicator start X position */
    private var progressPreviousX: Float = 0F

    /** progressIndicator end X position */
    private var progressX: Float = 0F

    /**
     * progressIndicator width
     * */
    var indicatorWidth: Float = 100F
        set(value) {
            field = value
            updateView()
        }

    /** indicator left side radius */
    private var radiusX: Float = RADIUS.dp

    /** indicator right side radius */
    private var radiusY: Float = RADIUS.dp

    /** [LoadingView] background color */
    private val backgroundPaint: Paint = Paint()

    /** [LoadingView] indicator paint */
    private val progressPaint: Paint = Paint()

    /** [LoadingView] indicator path */
    private val progressPath: Path = Path()

    /**
     * progress animation [LoadingAnimation].
     * default animations is [LoadingAnimation.NONE]
     */
    var loadingAnimation: LoadingAnimation = LoadingAnimation.NONE
        set(value) {
            field = value
            updateView()
        }

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.LoadingView, 0, 0)

        this.progressColor = attr.getColor(R.styleable.LoadingView_loading_color, progressColor)
        this.progressBackgroundColor = attr.getColor(
            R.styleable.LoadingView_loading_background_color,
            progressBackgroundColor
        )
        this.duration =
            attr.getInt(R.styleable.LoadingView_loading_duration, duration.toInt()).toLong()
        this.indicatorWidth =
            attr.getDimension(R.styleable.LoadingView_loading_indicator_width, indicatorWidth)
        this.radiusX = attr.getDimension(R.styleable.LoadingView_loading_radius, radiusX)
        this.radiusY = attr.getDimension(R.styleable.LoadingView_loading_radius, radiusY)

        this.loadingAnimation =
            when (attr.getInt(R.styleable.LoadingView_loading_animation, loadingAnimation.type)) {
                1 -> LoadingAnimation.BOUNCE
                2 -> LoadingAnimation.DECELERATE
                3 -> LoadingAnimation.ACCELERATE
                4 -> LoadingAnimation.ACCELERATEDECELERATE
                else -> LoadingAnimation.NONE
            }

        attr.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0F, 0F, width.toFloat(), height.toFloat(), backgroundPaint)
        progressPath.apply {
            reset()
            addRoundRect(
                progressPreviousX, 0F, progressX, height.toFloat(),
                floatArrayOf(
                    radiusX,
                    radiusX,
                    radiusY,
                    radiusY,
                    radiusY,
                    radiusY,
                    radiusX,
                    radiusX
                ),
                Path.Direction.CCW
            )
        }
        canvas?.drawPath(progressPath, progressPaint)
        super.onDraw(canvas)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        updateView()
    }

    private fun updateView() {
        drawBackground()
        drawProgress()
        post {
            autoAnimated()
        }
    }

    private fun drawBackground() {
        backgroundPaint.color = progressBackgroundColor
    }

    private fun drawProgress() {
        progressPaint.color = progressColor
    }

    private fun autoAnimated() {
        ValueAnimator.ofFloat(0f, 1f)
            .apply {
                duration = this@LoadingView.duration
                interpolator = loadingAnimation.getInterpolator()
                repeatCount = Animation.INFINITE
                addUpdateListener {
                    val progress = it.animatedValue as Float
                    progressPreviousX = width * progress
                    progressX =
                        if (((width.toFloat() * progress) + indicatorWidth) >= width.toFloat()) width.toFloat() else (width.toFloat() * progress) + indicatorWidth
                    if (progressX >= width.toFloat()) {
                        radiusY = 0F
                    }
                    invalidate()
                }
                doRepeat { radiusY = radiusX }
            }
            .also { it.start() }
    }

    companion object {
        private const val ANIM_DURATION = 3000L
        private const val RADIUS = 5
    }
}
