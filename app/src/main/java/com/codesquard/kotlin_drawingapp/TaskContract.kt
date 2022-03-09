package com.codesquard.kotlin_drawingapp

import android.graphics.Bitmap

interface TaskContract {

    interface TaskView {

        fun showRectangle(newRect: Rectangle)

        fun showSelectedRectangle()

        fun updateRectangle()

        fun showRectColor(color: String = "")

        fun showRectAlpha(alpha: Float = 0f)

        fun showEnabledColor(boolean: Boolean)

        fun showDraggingRectangle(tempRect: Rectangle?)

    }

    interface Presenter {

        fun addNewRectangle(width: Float, height: Float, photo: Bitmap? = null)

        fun selectRectangle(x: Float, y: Float)

        fun changeAlphaValue(value: Float)

        fun changeColor()

        fun dragRectangle(x: Float, y: Float)

    }

}