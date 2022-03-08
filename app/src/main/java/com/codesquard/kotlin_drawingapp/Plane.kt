package com.codesquard.kotlin_drawingapp

import android.graphics.Bitmap

class Plane(private val listener: RectangleListener) {

    private val rectangleList = mutableListOf<Rectangle>()
    private lateinit var newRect: Rectangle

    fun createNewRectangle(width: Float, height: Float, photo: Bitmap? = null) {
        photo?.run {
            val photoRect = PhotoRectangle()
            photoRect.setBitmap(this)
            newRect = photoRect
        } ?: run {
            newRect = NormalRectangle()
        }
        newRect = RectangleFactory(newRect).getInstance()
        newRect.setSize(width.toInt(), height.toInt())
        rectangleList.add(newRect)
        listener.onCreateRectangle(newRect)
    }

    fun getRectangle(index: Int) = rectangleList[index]

    fun countRectangle() = rectangleList.size

    fun selectRectangle(x: Float, y: Float) {
        val reversedRectList = rectangleList.reversed()
        unSelectRectangle(reversedRectList)

        reversedRectList.forEachIndexed { index, rect ->
            val rectFirstX = rect.point[0]
            val rectFirstY = rect.point[1] + 45
            val rectSecondX = rectFirstX + rect.size[0]
            val rectSecondY = rectFirstY + rect.size[1]
            val notReversedListIndex = reversedRectList.size - 1 - index

            if ((x in rectFirstX..rectSecondX) && (y in rectFirstY..rectSecondY)) {
                rect.isSelected(true)
                listener.onSelectRectangle(notReversedListIndex)
                return
            } else {
                listener.onSelectRectangle()
            }
        }
    }

    private fun unSelectRectangle(rectList: List<Rectangle>) {
        rectList.forEach {
            it.isSelected(false)
            listener.onSelectRectangle()
        }
    }

    fun updateAlpha(value: Float) {
        rectangleList.forEach {
            if (it.isSelected) {
                it.setAlpha((value * 25).toInt())
                listener.onUpdateRectangle()
                return
            }
        }
    }

    fun updateColor() {
        val r = (0..255).random()
        val g = (0..255).random()
        val b = (0..255).random()
        rectangleList.forEach {
            if (it.isSelected) {
                it.setColor(r, g, b)
                listener.onUpdateRectangle()
                return
            }
        }
    }
}