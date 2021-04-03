package com.pradeep.movieinfo.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.pradeep.movieinfo.R


class RatingView : View{

    private lateinit var mPaint: Paint
    private var percent = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr)

    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val rect = RectF()
    private val startAngle = -90f
    private val maxAngle = 360f
    private val maxProgress = 100

    private var diameter = 0f
    private var angle = 0f

    override fun onDraw(canvas: Canvas) {
        drawCircle(maxAngle, canvas, backgroundPaint)
        drawCircle(angle, canvas, progressPaint)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG);
        setRatingProgressWidth(8f)
        drawText(canvas)
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        diameter = Math.min(width, height).toFloat()
        updateRect()
    }

    private fun updateRect() {
        val strokeWidth = backgroundPaint.strokeWidth
        rect.set(strokeWidth, strokeWidth, diameter - strokeWidth, diameter - strokeWidth)
    }

    private fun drawCircle(angle: Float, canvas: Canvas, paint: Paint) {
        canvas.drawArc(rect, startAngle, angle, false, paint)
    }

    private fun calculateAngle(progress: Int) = maxAngle / maxProgress * progress

    fun setRatingProgress(progress: Int) {
        angle = calculateAngle(progress)
        if(progress <50){
            setRatingProgressColor(context.getColor(R.color.yellow))
            setRatingProgressBackgroundColor(context.getColor(R.color.ultra_light_yellow))
        }else{
            setRatingProgressColor(context.getColor(R.color.light_green))
            setRatingProgressBackgroundColor(context.getColor(R.color.ultra_light_green))
        }
        percent = progress
        invalidate()
    }

    fun setRatingProgressColor(color: Int) {
        progressPaint.color = color
        invalidate()
    }

    fun setRatingProgressBackgroundColor(color: Int) {
        backgroundPaint.color = color
        invalidate()
    }

    fun getRating() : String{
        return "${percent}%"
    }


    fun setRatingProgressWidth(width: Float) {
        progressPaint.strokeWidth = width
        backgroundPaint.strokeWidth = width
        updateRect()
        invalidate()
    }

    private fun drawText(canvas: Canvas) {
        mPaint.setTextSize(Math.min(width, height) / 3.5f)
        mPaint.setTextAlign(Paint.Align.CENTER)
        mPaint.setStrokeWidth(2f)
        mPaint.setColor(Color.WHITE)

        // Center text
        val xPos = canvas.width / 2
        val yPos = (canvas.height / 2 - (mPaint.descent() + mPaint.ascent()) / 2)
        canvas.drawText(
              "${percent}%",
            xPos.toFloat(), yPos , mPaint
        )
    }
}