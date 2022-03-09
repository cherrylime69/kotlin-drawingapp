package com.codesquard.kotlin_drawingapp

interface Rectangle {
    var id: String
    var point: Array<Float>
    var size: Array<Int>
    var color: Array<Int>
    var isSelected: Boolean
    var alphaValue: Int

    fun setPoint(x: Float, y: Float) {
        point = arrayOf(x, y)
    }

    fun setSize(width: Int, height: Int) {
        size = arrayOf(width, height)
    }

    fun setColor(r: Int, g: Int, b: Int) {
        color = arrayOf(r, g, b)
    }

    fun setAlpha(alpha: Int) {
        this.alphaValue = alpha
    }

    fun setID(id: String) {
        this.id = id
    }

    fun isSelected(boolean: Boolean = false) {
        this.isSelected = boolean
    }

    fun clone(): Rectangle

}