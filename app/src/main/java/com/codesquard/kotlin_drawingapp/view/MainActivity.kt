package com.codesquard.kotlin_drawingapp.view

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.codesquard.kotlin_drawingapp.*
import com.codesquard.kotlin_drawingapp.model.Rectangle
import com.codesquard.kotlin_drawingapp.presenter.TaskContract
import com.codesquard.kotlin_drawingapp.presenter.TaskPresenter
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), TaskContract.TaskView {

    private lateinit var mainLayout: ConstraintLayout
    private lateinit var customView: CustomView
    private lateinit var tempView: TemporaryView
    private lateinit var firstBtn: Button
    private lateinit var backgroundBtn: Button
    private lateinit var photoBtn: Button
    private lateinit var positionXBtn: Button
    private lateinit var positionYBtn: Button
    private lateinit var sizeWBtn: Button
    private lateinit var sizeHBtn: Button
    private lateinit var alphaSlider: Slider
    private lateinit var presenter: TaskContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout = findViewById(R.id.main_layout)
        customView = findViewById(R.id.custom_view)
        tempView = findViewById(R.id.temporary_view)
        backgroundBtn = findViewById(R.id.btn_background)
        photoBtn = findViewById(R.id.btn_photo_rect)
        alphaSlider = findViewById(R.id.slider_alpha)
        positionXBtn = findViewById(R.id.btn_position_x)
        positionYBtn = findViewById(R.id.btn_position_y)
        sizeWBtn = findViewById(R.id.btn_size_w)
        sizeHBtn = findViewById(R.id.btn_size_h)
        presenter = TaskPresenter(this)

        val getPhoto = registerIntentToGetPhotoAsBitmap()
        val requestPermissionLauncher = registerPermission(getPhoto)

        onClickRectBtn()
        onClickPhotoBtn(requestPermissionLauncher)
        onTouchBtnToChangeSize()
        onTouchBtnToChangePosition()
    }

    private fun onTouchBtnToChangeSize() {
        sizeWBtn.setOnTouchListener { v: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> presenter.changeSize(event.x, event.y, true)
                else -> v.performClick()
            }
            true
        }
        sizeHBtn.setOnTouchListener { v: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> presenter.changeSize(event.x, event.y, false)
                else -> v.performClick()
            }
            true
        }
    }

    private fun onTouchBtnToChangePosition() {
        positionXBtn.setOnTouchListener { v: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> presenter.changePosition(event.x, event.y, true)
                else -> v.performClick()
            }
            true
        }
        positionYBtn.setOnTouchListener { v: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> presenter.changePosition(event.x, event.y, false)
                else -> v.performClick()
            }
            true
        }
    }

    private fun registerIntentToGetPhotoAsBitmap() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val imageUri = it.data?.data ?: throw IllegalAccessError("사진을 선택해야 합니다.")
                val photo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            this.contentResolver,
                            imageUri
                        )
                    )
                } else {
                    MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                }
                val width = dpToPx(150f)
                val height = dpToPx(120f)
                presenter.addNewRectangle(width, height, photo)
            } else {
                Snackbar.make(customView, "사진을 불러오지 못하였습니다", Snackbar.LENGTH_SHORT).show()
            }
        }

    private fun registerPermission(photo: ActivityResultLauncher<Intent>): ActivityResultLauncher<String> {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    val intent = Intent(ACTION_GET_CONTENT).apply {
                        data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        type = "image/*"
                    }
                    photo.launch(intent)
                } else {
                    Snackbar.make(
                        customView,
                        "사진을 추가하기 위해서는 미디어 권한을 승인하시기 바랍니다",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        return requestPermissionLauncher
    }

    private fun onClickPhotoBtn(requestPermissionLauncher: ActivityResultLauncher<String>) {
        photoBtn.setOnClickListener {
            requestPermissionLauncher.launch("android.permission.ACCESS_MEDIA_LOCATION")
        }
    }

    private fun onClickColorBtn() {
        backgroundBtn.setOnClickListener {
            presenter.changeColor()
        }
    }

    private fun onSlideAlpha() {
        alphaSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}
            override fun onStopTrackingTouch(slider: Slider) {
                presenter.changeAlphaValue(slider.value)
            }
        })
    }

    private fun onClickRectBtn() {
        firstBtn = findViewById(R.id.btn_normal_rect)
        firstBtn.setOnClickListener {
            val width = dpToPx(150f)
            val height = dpToPx(120f)
            presenter.addNewRectangle(width, height)
        }
    }

    override fun showRectangle(newRect: Rectangle) {
        customView.addNewRect(newRect)
        customView.invalidate()
    }

    override fun showSelectedRectangle() {
        customView.invalidate()
    }

    override fun showRectColor(color: String) {
        backgroundBtn.text = color
    }

    override fun showRectAlpha(alpha: Float) {
        alphaSlider.value = alpha
    }

    override fun showEnabledColor(boolean: Boolean) {
        backgroundBtn.isEnabled = boolean
    }

    override fun showDraggingRectangle(tempRect: Rectangle?) {
        tempView.setTempRect(tempRect)
        tempView.invalidate()
    }

    private fun showDraggedRectangle() {
        tempView.setTempRect(null)
        tempView.invalidate()
        customView.invalidate()
    }

    override fun enableRectColorBtnAndSlider() {
        onClickColorBtn()
        onSlideAlpha()
    }

    override fun showRectSize(w: Float, h: Float) {
        val width = pxToDp(w).toInt()
        val height = pxToDp(h).toInt()
        sizeWBtn.text = "W   $width"
        sizeHBtn.text = "H   $height"
    }

    override fun showRectPosition(x: String, y: String) {
        positionXBtn.text = "X   $x"
        positionYBtn.text = "Y   $y"
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x ?: 0f
        val y = event?.y ?: 0f

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> presenter.selectRectangle(x, y)
            MotionEvent.ACTION_MOVE -> {
                presenter.dragRectangle(x, y)
            }
            MotionEvent.ACTION_UP -> showDraggedRectangle()
        }
        return super.onTouchEvent(event)
    }

    private fun dpToPx(dp: Float): Float {
        val resources = this.resources
        val metrics = resources.displayMetrics
        val density = metrics.density
        return dp * density
    }

    private fun pxToDp(px: Float): Float {
        val resources = this.resources
        val metrics = resources.displayMetrics
        val density = metrics.density
        return px / density
    }
}


