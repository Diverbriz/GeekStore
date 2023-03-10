package com.example.geekstore.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class CustomClock(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mHeight = 0
    private var mWidth = 0
    private var mRadius = 0
    private var mAngle = 0.0
    private var mCentreX = 0
    private var mCentreY = 0
    private var mPadding = 0
    private var mIsInit = false
    private var mPaint: Paint? = null
    private var mPath: Path? = null
    private lateinit var mNumbers :IntArray
    private var mMinimum = 0
    private var mHour = 0f
    private var mMinute = 0f
    private var mSecond = 0f
    private var mHourHandSize = 170f
    private var mHandSize = 0
    private var mRect :Rect? = null
    private var mDots = 0

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!mIsInit){
            init()
        }
        if (canvas != null) {
            drawCircle(canvas)
            drawHands(canvas)
            drawNumerals(canvas);

        }
        postInvalidateDelayed(50);
    }

    private fun init() {
        mHeight = height
        mWidth = width
        mPadding = 50
        mCentreX = mWidth / 2
        mCentreY = mHeight / 2
        mMinimum = mHeight.coerceAtMost(mWidth)
        mRadius =
            mMinimum / 2 - mPadding
        mAngle =(Math.PI / 30 - Math.PI / 2).toFloat().toDouble()
        mPaint = Paint()
        mPath = Path()
        mRect = Rect()
        mHandSize =
            mRadius - mRadius / 4
        mNumbers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        mDots = mNumbers.size*5
        mIsInit = true
    }

    private fun setPaintAttributes(colour: Int, stroke: Paint.Style, strokeWidth: Int) {
        mPaint?.reset()
        mPaint?.color = colour
        mPaint?.style = stroke
        mPaint?.strokeWidth = strokeWidth.toFloat()
        mPaint?.isAntiAlias = true
    }

    private fun drawCircle(canvas: Canvas) {
        mPaint?.reset()
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 20)
        mPaint?.let {
            canvas.drawCircle(mCentreX.toFloat(), mCentreY.toFloat(), mRadius.toFloat(),
                it
            )
        }
    }

    private fun drawHands(canvas: Canvas) {
        val calendar = Calendar.getInstance()
        print(calendar.timeInMillis)
        mHour =
            calendar[Calendar.HOUR_OF_DAY].toFloat() //convert to 12hour format from 24 hour format

        mHour = if (mHour > 12) mHour - 12 else mHour
        mMinute = calendar[Calendar.MINUTE].toFloat()
        mSecond = calendar[Calendar.SECOND].toFloat()
        Log.e("Tag", "$mHour, $mMinute")

        drawHourHand(canvas, (mHour + mMinute / 60.0) * 5f)
        drawMinuteHand(canvas, mMinute)
        drawSecondsHand(canvas, mSecond)
    }

    private fun drawMinuteHand(canvas: Canvas, location: Float) {
        mPaint?.reset();
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE,8);
        mAngle = Math.PI * location / 30 - Math.PI / 2;
        mPaint?.let {
            canvas.drawLine(
                mCentreX.toFloat(),
                mCentreY.toFloat(),
                (mCentreX + Math.cos(mAngle)*mHourHandSize).toFloat(),
                (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat(),
                it,
            )
        };
    }

    private fun drawHourHand(canvas: Canvas, location: Double) {
        mPaint?.reset()
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 10)
        mAngle = Math.PI * location / 30 - Math.PI / 2
        canvas.drawLine(
            mCentreX.toFloat(),
            mCentreY.toFloat(),
            (mCentreX + Math.cos(mAngle) * mHandSize).toFloat(),
            (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat(),
            mPaint!!
        )
    }

    private fun drawSecondsHand(canvas: Canvas, location: Float) {
        mPaint?.reset()
        setPaintAttributes(Color.RED, Paint.Style.STROKE, 8)
        mAngle = Math.PI * location / 30 - Math.PI / 2
        mPaint?.let {
            canvas.drawLine(
                mCentreX.toFloat(),
                mCentreY.toFloat(),
                (mCentreX + cos(mAngle) * mHandSize).toFloat(),
                (mCentreY + sin(mAngle) * mHourHandSize).toFloat(),
                it
            )
        }
    }

    private fun drawNumerals(canvas: Canvas) {
        mPaint!!.textSize = if (mMinimum > 150) 16 * resources.displayMetrics.density else 0F
        for (number in mNumbers) {
            val num = number.toString()
            mPaint?.getTextBounds(num, 0, num.length, mRect)
            val angle = Math.PI / 6 * (number - 3)
            val x = (mCentreX + cos(angle) * 2/3 * mRadius - mRect!!.width() / 2)

            val y = (mCentreY + sin(angle) * 2/3 * mRadius + mRect!!.height() / 2)
            canvas.drawText(num, x.toFloat(), y.toFloat(), mPaint!!)
        }
    }



}