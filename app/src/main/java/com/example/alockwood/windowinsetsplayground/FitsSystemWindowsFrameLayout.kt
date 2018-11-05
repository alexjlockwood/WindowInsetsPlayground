package com.example.alockwood.windowinsetsplayground

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class FitsSystemWindowsFrameLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val consumeLeftInset: Boolean
    private val consumeTopInset: Boolean
    private val consumeRightInset: Boolean
    private val consumeBottomInset: Boolean

    @ColorInt
    private val statusBarColor: Int
    private val isLightStatusBar: Boolean

    @ColorInt
    private val navigationBarColor: Int
    @ColorInt
    private val navigationBarDividerColor: Int
    private val isLightNavigationBar: Boolean

    private var lastWindowInsetTop: Int = 0
    private var lastWindowInsetBottom: Int = 0

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val navigationBarDividerHeight: Float

    init {
        systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        paint.style = Paint.Style.FILL
        navigationBarDividerHeight = resources.getDimension(R.dimen.navigation_bar_divider_height)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.FitsSystemWindowsFrameLayout, defStyleAttr, 0)
        try {
            consumeLeftInset = ta.getBoolean(R.styleable.FitsSystemWindowsFrameLayout_consumeLeftInset, true)
            consumeTopInset = ta.getBoolean(R.styleable.FitsSystemWindowsFrameLayout_consumeTopInset, true)
            consumeRightInset = ta.getBoolean(R.styleable.FitsSystemWindowsFrameLayout_consumeRightInset, true)
            consumeBottomInset = ta.getBoolean(R.styleable.FitsSystemWindowsFrameLayout_consumeBottomInset, true)
            statusBarColor = ta.getColor(R.styleable.FitsSystemWindowsFrameLayout_statusBarColor, Color.WHITE)
            isLightStatusBar = ta.getBoolean(R.styleable.FitsSystemWindowsFrameLayout_isLightStatusBar, true)
            if (isLightStatusBar) {
                SystemUiVisibilityUtils.setLightStatusBar(this)
            }
            navigationBarColor = ta.getColor(R.styleable.FitsSystemWindowsFrameLayout_navigationBarColor, Color.BLACK)
            navigationBarDividerColor = ta.getColor(R.styleable.FitsSystemWindowsFrameLayout_navigationBarDividerColor, Color.TRANSPARENT)
            isLightNavigationBar = ta.getBoolean(R.styleable.FitsSystemWindowsFrameLayout_isLightNavigationBar, false)
            if (isLightNavigationBar) {
                SystemUiVisibilityUtils.setLightNavigationBar(this)
            }
        } finally {
            ta.recycle()
        }

        setOnApplyWindowInsetsListener { _, insets ->
            lastWindowInsetTop = insets.systemWindowInsetTop
            lastWindowInsetBottom = insets.systemWindowInsetBottom
            setWillNotDraw(lastWindowInsetTop == 0 && lastWindowInsetBottom == 0 && background == null);
            setPadding(
                    if (consumeLeftInset) insets.systemWindowInsetLeft else 0,
                    if (consumeTopInset) insets.systemWindowInsetTop else 0,
                    if (consumeRightInset) insets.systemWindowInsetRight else 0,
                    if (consumeBottomInset) insets.systemWindowInsetBottom else 0
            )
            insets.replaceSystemWindowInsets(
                    if (consumeLeftInset) 0 else insets.systemWindowInsetLeft,
                    if (consumeTopInset) 0 else insets.systemWindowInsetTop,
                    if (consumeRightInset) 0 else insets.systemWindowInsetRight,
                    if (consumeBottomInset) 0 else insets.systemWindowInsetBottom
            )
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        requestApplyInsets()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val isMarshmallow = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        val statusBarColor = if (isMarshmallow || !isLightStatusBar) this.statusBarColor else Color.BLACK
        drawRect(canvas, statusBarColor, 0, lastWindowInsetTop)

        val isOreo = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        val navigationBarColor = if (isOreo || !isLightNavigationBar) this.navigationBarColor else Color.BLACK
        val navigationBarDividerColor = if (isOreo || !isLightNavigationBar) this.navigationBarDividerColor else Color.TRANSPARENT

        val navigationBarTop = (height - lastWindowInsetBottom).toFloat()
        drawRect(canvas, navigationBarColor, navigationBarTop, height.toFloat())
        drawRect(canvas, navigationBarDividerColor, navigationBarTop, navigationBarTop + navigationBarDividerHeight)
    }

    private fun drawRect(canvas: Canvas, @ColorInt color: Int, top: Int, bottom: Int) {
        drawRect(canvas, color, top.toFloat(), bottom.toFloat())
    }

    private fun drawRect(canvas: Canvas, @ColorInt color: Int, top: Float, bottom: Float) {
        paint.color = color
        canvas.drawRect(0f, top, width.toFloat(), bottom, paint)
    }
}