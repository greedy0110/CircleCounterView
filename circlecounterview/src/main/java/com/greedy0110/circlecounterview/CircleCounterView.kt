package com.greedy0110.circlecounterview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes

class CircleCounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    // -90f 가 top 포지션부터 시작함을 의미함.
    var startAngle: Float = -90f
    var angle: Float = 120f
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    var text: String = "13"
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    var circleWidth = 10f
        set(value) {
            field = value
            setCirclePaint()
            invalidate()
            requestLayout()
        }
    var textSize = 26f
        set(value) {
            field = value
            setCounterPaint()
            invalidate()
            requestLayout()
        }
    @ColorRes
    var circleColor: Int = R.color.colorRed
        set(value) {
            field = value
            setCirclePaint()
            invalidate()
            requestLayout()
        }
    @ColorRes
    var reverseCircleColor: Int = R.color.colorRed
        set(value) {
            field = value
            setCirclePaint()
            invalidate()
            requestLayout()
        }
    @ColorRes
    var textColor: Int = R.color.colorBlack
        set(value) {
            field = value
            setCounterPaint()
            invalidate()
            requestLayout()
        }
    @ColorRes
    var circleInnerColor: Int = R.color.transparent
        set(value) {
            field = value
            setCircleInnerPaint()
            invalidate()
            requestLayout()
        }


    private var circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = circleColor
        strokeWidth = circleWidth
        style = Paint.Style.STROKE
    }
    private var counterPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor
        textSize = textSize
        textAlign = Paint.Align.CENTER
    }
    private var circleInnerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = circleInnerColor
        style = Paint.Style.FILL
    }
    private var reverseCirclePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = reverseCircleColor
        strokeWidth = circleWidth
        style = Paint.Style.STROKE
    }

    private fun setCircleInnerPaint() {
        circleInnerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = circleInnerColor
            style = Paint.Style.FILL
        }
    }

    private fun setCirclePaint() {
        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = circleColor
            strokeWidth = circleWidth
            style = Paint.Style.STROKE
            //TODO: 이거 세팅 값으로 변경하기
//            strokeJoin = Paint.Join.ROUND
//            strokeCap = Paint.Cap.ROUND
//            pathEffect = CornerPathEffect(10f)
        }

        reverseCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = reverseCircleColor
            strokeWidth = circleWidth
            style = Paint.Style.STROKE
        }
    }

    private fun setCounterPaint() {
        counterPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = textColor
            textSize = this@CircleCounterView.textSize
            textAlign = Paint.Align.CENTER
        }
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleCounterView,
            defStyleAttr, 0
        ).apply {
            try {
                startAngle = getFloat(R.styleable.CircleCounterView_startAngle, -90f)
                circleWidth = getDimension(
                    R.styleable.CircleCounterView_circleWidth,
                    26f
                )
                @Suppress("DEPRECATION")
                circleColor = getColor(
                    R.styleable.CircleCounterView_circleColor,
                    resources.getColor(R.color.colorRed)
                )
                @Suppress("DEPRECATION")
                reverseCircleColor = getColor(
                    R.styleable.CircleCounterView_reverseCircleColor,
                    resources.getColor(R.color.transparent)
                )
                textSize = getDimension(
                    R.styleable.CircleCounterView_textSize,
                    26f
                )
                @Suppress("DEPRECATION")
                textColor = getColor(
                    R.styleable.CircleCounterView_textColor,
                    resources.getColor(R.color.colorBlack)
                )
                text = getString(
                    R.styleable.CircleCounterView_text
                ).orEmpty()
                @Suppress("DEPRECATION")
                circleInnerColor = getColor(
                    R.styleable.CircleCounterView_circleInnerColor,
                    resources.getColor(R.color.transparent)
                )
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawOval(
                paddingLeft + circleWidth * 1.5f,
                paddingTop + circleWidth * 1.5f,
                width.toFloat() - paddingRight - circleWidth * 1.5f,
                height.toFloat() - paddingBottom - circleWidth * 1.5f,
                circleInnerPaint
            )

            drawArc(
                paddingLeft + circleWidth,
                paddingTop + circleWidth,
                width.toFloat() - paddingRight - circleWidth,
                height.toFloat() - paddingBottom - circleWidth,
                startAngle,
                angle,
                false,
                circlePaint
            )

            drawArc(
                paddingLeft + circleWidth,
                paddingTop + circleWidth,
                width.toFloat() - paddingRight - circleWidth,
                height.toFloat() - paddingBottom - circleWidth,
                startAngle + angle,
                360f - angle,
                false,
                reverseCirclePaint
            )

            drawText(text, width / 2f, height / 2f, counterPaint)
        }
    }
}
