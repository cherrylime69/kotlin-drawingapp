package com.codesquard.kotlin_drawingapp

class TaskPresenter(val taskView: TaskContract.TaskView) : TaskContract.Presenter,
    RectangleListener {

    private val plane = Plane(this)

    override fun addNewRectangle() {
        plane.createNewRectangle()
    }

    override fun selectRectangle(x: Float, y: Float) {
        plane.selectRectangle(x, y)
    }

    override fun onCreateRectangle(newRect: Rectangle) {
        taskView.showRectangle(newRect)
    }

    override fun onSelectRectangle() {
        taskView.showSelectedRectangleOrNoRectangle()
    }

    override fun onSelectNoRectangle() {
        taskView.showSelectedRectangleOrNoRectangle()
    }

}


