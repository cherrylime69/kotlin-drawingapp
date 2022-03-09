package com.codesquard.kotlin_drawingapp

import android.graphics.Bitmap

class TaskPresenter(private val taskView: TaskContract.TaskView) : TaskContract.Presenter,
    RectangleListener {

    private val plane = Plane(this)
    private var selectedRectIndex = -1

    override fun addNewRectangle(width: Float, height: Float, photo: Bitmap?) {
        plane.createNewRectangle(width, height, photo)
    }

    override fun selectRectangle(x: Float, y: Float) {
        plane.selectRectangle(x, y)
    }

    override fun changeAlphaValue(value: Float) {
        plane.updateAlpha(value)
    }

    override fun changeColor() {
        plane.updateColor()
    }

    override fun dragRectangle(x: Float, y: Float) {
        plane.dragRectangle(x, y)
    }

    override fun onCreateRectangle(newRect: Rectangle) {
        taskView.showRectangle(newRect)
    }

    override fun onSelectRectangle(index: Int) {
        selectedRectIndex = index
        if (index > -1) {
            taskView.updateRectangle()
        }
        getRectAlpha()
        getRectColor()
        taskView.showSelectedRectangle()
    }

    override fun onUpdateRectangle() {
        getRectColor()
        taskView.showSelectedRectangle()
    }

    override fun onDragRectangle(tempRect: Rectangle?) {
        taskView.showDraggingRectangle(tempRect)
    }

    private fun getRectColor() {
        when (true) {
            selectedRectIndex == -1 -> taskView.showRectColor()
            plane.getRectangle(selectedRectIndex) is PhotoRectangle -> {
                val colorText = "None"
                taskView.showRectColor(colorText)
                taskView.showEnabledColor(false)
            }
            else -> {
                val color = plane.getRectangle(selectedRectIndex).color
                val colorText =
                    "#${color[0].toString(16)}${color[1].toString(16)}${color[2].toString(16)}"
                taskView.showRectColor(colorText)
                taskView.showEnabledColor(true)
            }
        }
    }

    private fun getRectAlpha() {
        if (selectedRectIndex == -1) {
            taskView.showRectAlpha()
        } else {
            val alpha = plane.getRectangle(selectedRectIndex).alphaValue / 25
            taskView.showRectAlpha(alpha.toFloat())
        }
    }

}


