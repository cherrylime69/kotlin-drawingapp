package com.codesquard.kotlin_drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

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
                    strokeWidth = 10f
                }
                canvas?.drawRect(x, y, width, height, strokePaint)
            }

            if (it is PhotoRectangle) {
                val photo: Bitmap = it.getPhoto() ?: return
                val rect = RectF(x, y, width, height)
                canvas?.drawBitmap(photo, null, rect, paint)
            } else {
                canvas?.drawRect(x, y, width, height, paint)
            }

        }
    }

}

