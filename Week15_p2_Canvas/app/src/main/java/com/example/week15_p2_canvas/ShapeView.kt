package com.example.week15_p2_canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import androidx.core.content.res.ResourcesCompat

class ShapeView(context: Context?) : View(context) {
    private var mShapeDraw: ShapeDrawable = ShapeDrawable(OvalShape())
    private lateinit var mPaint: Paint
    // 為以上兩變數，設定初始設定
    init {
        mShapeDraw.paint.color = Color.rgb(255,255,255)
        mPaint = Paint()
        mPaint.setAntiAlias(true)
        mPaint.setColor(Color.CYAN)
        mPaint.textSize = 60f
    }
    protected override fun onDraw(canvas: Canvas) {
// TODO Auto-generated method stub
        super.onDraw(canvas)
        mShapeDraw.setBounds(10, 10, getWidth() / 2 - 10, getHeight() / 2 - 20)
        mShapeDraw.draw(canvas)
        canvas.drawOval(RectF(getWidth() / 2 + 10f,10f,getWidth() - 10f,getHeight() / 2 - 20f), mPaint)
        canvas.drawText("My draw text",10f,getHeight() / 2f, mPaint)
        canvas.drawLine(getWidth() / 2 + 10f,getHeight() / 2 - 10f,getWidth() - 10f,getHeight() / 2f, mPaint)
        var drawImg = ResourcesCompat.getDrawable(getResources(),
            android.R.drawable.ic_lock_idle_alarm, null)!!
        drawImg.setBounds(10,getHeight() / 2 + 10,getWidth() / 2 - 10,getHeight() * 3 / 4)
        drawImg.draw(canvas)
    }
}
