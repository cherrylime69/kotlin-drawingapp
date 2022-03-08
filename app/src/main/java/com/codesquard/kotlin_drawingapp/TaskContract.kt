package com.codesquard.kotlin_drawingapp

import android.graphics.Bitmap

interface TaskContract {

    interface TaskView {

        fun showRectangle(newRect: Rectangle)

        fun showSelectedRectangle()

        fun updateRect()

        fun showRectColor(color: String = "")

        fun showRectAlpha(alpha: Float = 0f)

    }

    interface Presenter {

        fun addNewRectangle(width: Float, height: Float)

        fun selectRectangle(x: Float, y: Float)

        fun changeAlphaValue(value: Float)

        fun changeColor()

        fun addNewPhoto(photo: Bitmap, width: Float, height: Float)

    }

}