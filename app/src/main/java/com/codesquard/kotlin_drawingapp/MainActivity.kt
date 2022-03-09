package com.codesquard.kotlin_drawingapp

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), TaskContract.TaskView {

    private lateinit var firstBtn: Button
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var backgroundBtn: Button
    private lateinit var alphaSlider: Slider
    private lateinit var photoBtn: Button
    private lateinit var presenter: TaskContract.Presenter
    private lateinit var customView: CustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout = findViewById(R.id.main_layout)
        customView = findViewById(R.id.customView)
        backgroundBtn = findViewById(R.id.background_btn)
        photoBtn = findViewById(R.id.photo_btn)
        alphaSlider = findViewById(R.id.slider_alpha)
        presenter = TaskPresenter(this)

        val getPhoto = registerIntentToGetPhotoAsBitmap()
        val requestPermissionLauncher = registerPermission(getPhoto)

        onClickRectBtn()
        onClickPhotoBtn(requestPermissionLauncher)
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
                val width = dp2px(150f)
                val height = dp2px(120f)
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
        firstBtn = findViewById(R.id.create_btn)
        firstBtn.setOnClickListener {
            val width = dp2px(150f)
            val height = dp2px(120f)
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

    override fun showDraggingRectangle(temRectangle: Rectangle) {
        customView.setTempRect(temRectangle)
        customView.invalidate()
    }

    override fun updateRectangle() {
        onClickColorBtn()
        onSlideAlpha()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x ?: 0f
        val y = event?.y ?: 0f

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> presenter.selectRectangle(x, y)
            MotionEvent.ACTION_MOVE -> {
                presenter.dragRectangle(x, y)
            }
            MotionEvent.ACTION_UP -> return true
        }

        return super.onTouchEvent(event)
    }

    private fun dp2px(dp: Float): Float {
        val resources = this.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}


