package com.codesquard.kotlin_drawingapp

class RectangleFactory {
    private val rectangle = Rectangle()

    private fun setRectangleID() {
        val letter = "abcdefghijklmnopqrstuvwxyz0123456789"
        var randomID = ""
        (1..9).forEach {
            randomID += letter.random()
            if (it == 3 || it == 6)
                randomID += "-"
        }
        rectangle.setID(randomID)
    }

    private fun setRectanglePoint() {
        val pointX = (0..1200).random().toFloat()
        val pointY = (0..1000).random().toFloat()
        rectangle.setPoint(pointX, pointY)
    }

    private fun setRectangleSize() {
        val width = 150
        val height = 120
        rectangle.setSize(width, height)
    }

    private fun setRectangleColor() {
        val r = (0..255).random()
        val g = (0..255).random()
        val b = (0..255).random()
        rectangle.setColor(r, g, b)
    }

    private fun setRectangleAlpha() {
        val alpha = (0..255).random()
        rectangle.setAlpha(alpha)
    }

    fun getInstance(): Rectangle {
        setRectangleID()
        setRectanglePoint()
        setRectangleColor()
        setRectangleAlpha()
        setRectangleSize()
        return rectangle
    }

    fun printInfo(): String {
        return rectangle.toString()
    }

}

class Rectangle {
    private lateinit var id: String
    private lateinit var point: Array<Float>
    private lateinit var size: Array<Int>
    private lateinit var color: Array<Int>
    private var isSelected: Boolean = false
    private var alpha: Int = 0

    fun setPoint(x: Float, y: Float) {
        point = arrayOf(x, y)
    }

    fun getPoint() = point

    fun setSize(width: Int, height: Int) {
        size = arrayOf(width, height)
    }

    fun getSize() = size

    fun setColor(r: Int, g: Int, b: Int) {
        color = arrayOf(r, g, b)
    }

    fun getColor() = color

    fun setAlpha(alpha: Int = 0) {
        this.alpha = alpha
    }

    fun getAlpha() = alpha

    fun setID(id: String) {
        this.id = id
    }

    fun isSelected(boolean: Boolean = false) {
        this.isSelected = boolean
    }

    fun getStatus() = isSelected

    fun getID() = id

    override fun toString(): String {
        return "(${id}), X:${point[0]}, Y:${point[1]}, W150, H120, R:${color[0]}, G:${color[1]}, B:${color[2]}, Alpha: ${alpha}"
    }
}