package com.codesquard.kotlin_drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CustomView(context: Context, attr: AttributeSet) : View(context, attr) {

    private val rectangleList = mutableListOf<Rectangle>()
    private var tempRect: Rectangle? = null

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

            tempRect?.apply {
                dragRectangle(tempRect, canvas)
            }
        }
    }

    private fun dragRectangle(tempRect: Rectangle?, canvas: Canvas?) {
        tempRect?.apply temp@{
            val startX = tempRect.point[0]
            val startY = tempRect.point[1]
            val endX = startX + tempRect.size[0]
            val endY = startX + tempRect.size[1]
            val paint = Paint().apply {
                this.color = Color.argb(
                    this@temp.alphaValue,
                    this@temp.color[0],
                    this@temp.color[1],
                    this@temp.color[2]
                )
            }

            if (this is PhotoRectangle) {
                val photo: Bitmap = this.getPhoto() ?: return
                val rect = RectF(startX, startY, endX, endY)
                canvas?.drawBitmap(photo, null, rect, paint)
            } else {
                canvas?.drawRect(startX, startY, endX, endY, paint)
            }
        }
    }

    fun setTempRect(tempRect: Rectangle) {
        this.tempRect = tempRect
    }

}

