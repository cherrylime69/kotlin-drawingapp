package com.codesquard.kotlin_drawingapp.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.codesquard.kotlin_drawingapp.model.PhotoRectangle
import com.codesquard.kotlin_drawingapp.model.Rectangle
import com.codesquard.kotlin_drawingapp.model.TextRectangle

class CustomView(context: Context, attr: AttributeSet) : View(context, attr) {

    private val rectangleList = mutableListOf<Rectangle>()

    fun addNewRect(newRect: Rectangle) {
        rectangleList.add(newRect)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawRectangle(rectangleList, canvas)
    }

    private fun drawRectangle(rectangleList: MutableList<Rectangle>, canvas: Canvas?) {
        rectangleList.forEach {
            val paint = Paint()
            val alpha = it.alphaValue
            val r = it.color[0]
            val g = it.color[1]
            val b = it.color[2]

            val x = it.point[0]
            val y = it.point[1]
            val width = it.size[0] + x
            val height = it.size[1] + y

            paint.color = Color.argb(alpha, r, g, b)

            if (it.isSelected) {
                val strokePaint = Paint().apply {
                    color = Color.rgb(r, g, b)
                    style = Paint.Style.STROKE
                    strokeWidth = 5f
                }
                canvas?.drawRect(x - 2.5f, y - 2.5f, width + 2.5f, height + 2.5f, strokePaint)
            }

            when (it) {
                is PhotoRectangle -> {
                    val photo: Bitmap = it.getPhoto() ?: return
                    val rect = RectF(x, y, width, height)
                    canvas?.drawBitmap(photo, null, rect, paint)
                }
                is TextRectangle -> {
                    val text = it.getText()
                    val textBound = Rect()
                    paint.textSize = 50f
                    paint.getTextBounds(text, 0, text.length, textBound)
                    val rY = textBound.top.toFloat()
                    canvas?.drawText(text, x, y - rY, paint)
                }
                else -> {
                    canvas?.drawRect(x, y, width, height, paint)
                }
            }
        }
    }

    fun measureTextSize(textRect: Rectangle): Array<Int> {
        val textRect = textRect as TextRectangle
        val text = textRect.getText()
        val textBound = Rect()
        val paint = Paint()
        paint.run {
            this.textSize = 50f
            this.getTextBounds(text, 0, text.length, textBound)
        }
        return arrayOf(textBound.width(), textBound.height())
    }
}

