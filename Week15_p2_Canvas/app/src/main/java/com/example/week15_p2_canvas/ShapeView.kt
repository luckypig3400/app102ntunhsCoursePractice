package com.example.week15_p2_canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.view.View
import androidx.core.content.res.ResourcesCompat

class ShapeView(context: Context?) : View(context) {
    private var ovalShape: ShapeDrawable = ShapeDrawable(OvalShape())
    private lateinit var mPaint: Paint
    private lateinit var linePaint: Paint

    private var rectShape: ShapeDrawable = ShapeDrawable(RectShape())

    // 為以上兩變數，設定初始設定
    init {
        ovalShape.paint.color = Color.rgb(87, 182, 208)
        // 水藍色比較好看 http://zh.tacolor.com/hex/57b6d0/
        rectShape.paint.color = Color.rgb(208, 113, 87)

        mPaint = Paint()
        mPaint.setAntiAlias(true)
        mPaint.setColor(Color.RED)
        mPaint.textSize = 48f

        linePaint = Paint()
        linePaint.strokeWidth = 5f
        linePaint.setColor(Color.YELLOW)
    }

    protected override fun onDraw(canvas: Canvas) {
// TODO Auto-generated method stub
        super.onDraw(canvas)

        //ovalShape.setBounds(10, 10, getWidth() / 2 - 10, getWidth() / 2 - 10)
        //ovalShape.draw(canvas) // 用上方的語法繪製出正圓形
        //canvas.drawOval(RectF(getWidth() / 2 + 10f,10f,getWidth() - 10f,getHeight() / 2 - 20f), mPaint) //用Canvas 語法繪製橢圓形

        var drawImg = ResourcesCompat.getDrawable(
            getResources(),
            R.drawable.hello5_max_w1024, null
        )!!
        drawImg.setBounds(60, 60, width - 60, height * 1 / 2 - 60)
        drawImg.draw(canvas)

        ovalShape.setBounds(
            width / 2 - 300,
            height / 2,
            width / 2 + 300,
            height / 2 + 600
        )
        ovalShape.draw(canvas)

        canvas.drawText(
            "V-tex Hello White",
            width / 3 - 6f,
            height / 2 + 168f,
            mPaint
        )

        canvas.drawLine(
            69f,
            height / 2 - 30f,
            width - 69f,
            height / 2 - 30f,
            linePaint
        )

        rectShape.setBounds(
            36,
            height - 240,
            width - 36,
            height - 36
        )
        rectShape.draw(canvas)

        mPaint.setColor(Color.BLACK)
        canvas.drawText(
            "V-TEX地表最強防水鞋，真正完全防水！",
            width / 6 - 18f,
            height - 168f,
            mPaint
        )
    }
}
