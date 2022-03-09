package com.codesquard.kotlin_drawingapp

class NormalRectangle : Rectangle {
    override lateinit var id: String
    override lateinit var point: Array<Float>
    override lateinit var size: Array<Int>
    override lateinit var color: Array<Int>
    override var isSelected: Boolean = false
    override var alphaValue: Int = 0
}
