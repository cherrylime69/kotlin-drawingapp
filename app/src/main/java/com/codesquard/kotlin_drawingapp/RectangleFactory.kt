package com.codesquard.kotlin_drawingapp

class RectangleFactory(private val rectangle: Rectangle) {

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

    override fun toString(): String {
        return "(${rectangle.id}), X:${rectangle.point[0]}, Y:${rectangle.point[0]}, W${rectangle.size[0]}, H${rectangle.size[1]}, R:${rectangle.color[0]}, G:${rectangle.color[1]}, B:${rectangle.color[2]}, Alpha: ${rectangle.alphaValue}"
    }

}