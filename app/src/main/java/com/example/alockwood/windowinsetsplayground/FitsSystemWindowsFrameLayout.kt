package com.example.alockwood.windowinsetsplayground

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class FitsSystemWindowsFrameLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val statusBarBackground: Drawable
    private var lastWindowInsetTop: Int = 0

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FitsSystemWindowsFrameLayout, defStyleAttr, 0)
        val statusBarBackground =
                ta.getDrawable(R.styleable.FitsSystemWindowsFrameLayout_statusBarBackground) ?: ColorDrawable(Color.WHITE)
        val isLightStatusBar =
                ta.getBoolean(R.styleable.FitsSystemWindowsFrameLayout_isLightStatusBar, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.statusBarBackground = statusBarBackground
            if (isLightStatusBar) {
                var flags = systemUiVisibility
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                systemUiVisibility = flags
            }
        } else {
            this.statusBarBackground = if (isLightStatusBar) ColorDrawable(Color.BLACK) else statusBarBackground
        }
        ta.recycle()

        setOnApplyWindowInsetsListener { _, insets ->
            lastWindowInsetTop = insets.systemWindowInsetTop
            setWillNotDraw(lastWindowInsetTop == 0 && getBackground() == null);
            setPadding(
                    insets.systemWindowInsetLeft,
                    insets.systemWindowInsetTop,
                    insets.systemWindowInsetRight,
                    insets.systemWindowInsetBottom
            )
            insets.consumeSystemWindowInsets()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        requestApplyInsets()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        statusBarBackground.setBounds(0, 0, width, lastWindowInsetTop)
        statusBarBackground.draw(canvas)
    }
}