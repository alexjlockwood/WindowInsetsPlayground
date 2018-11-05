package com.example.alockwood.windowinsetsplayground

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 * A [FrameLayout] that is cabable of consuming a screen's window insets and drawing its status bar
 * and navigation bar backgrounds. This class should typically be used as a screen's root view.
 *
 * By default, this view consumes all window insets and sets them as its padding to ensure that
 * children views aren't displayed underneath the status bar and navigation bars. This behavior can
 * be customized using the following attributes:
 *
 * - [app:consumeLeftInset][R.attr.consumeLeftInset]
 * - [app:consumeTopInset][R.attr.consumeTopInset]
 * - [app:consumeRightInset][R.attr.consumeRightInset]
 * - [app:consumeBottomInset][R.attr.consumeBottomInset]
 *
 * By default, this view draws a light status bar w/ a white background. This behavior can be
 * customized using the following attributes:
 *
 * - [app:statusBarColor][R.attr.statusBarColor]
 * - [app:isLightStatusBar][R.attr.isLightStatusBar]
 *
 * Note that light status bars are only supported on API 23 and above. If a light status bar is used
 * on an older platform, a dark status bar w/ a black background will be used instead.
 *
 * By default, this view draws a dark navigation bar w/ a black background. This behavior can be
 * customized using the following attributes:
 *
 * - [app:navigationBarColor][R.attr.navigationBarColor]
 * - [app:isLightNavigationBar][R.attr.isLightNavigationBar]
 *
 * Note that light navigation bars are only supported on API 26 and above. If a light navigation bar
 * is used on an older platform, a dark navigation bar w/ a black background will be used instead.
 *
 * By default, this view draws a divider above the navigation bar if it is light. This behavior can
 * be customized using the following attribute:
 *
 * - [app:showNavigationBarDivider][R.attr.showNavigationBarDivider]
 */
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
    private val isLightNavigationBar: Boolean
    private val showNavigationBarDivider: Boolean

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val navigationBarDividerHeight: Float
    @ColorInt
    private val navigationBarDividerColor: Int

    private var lastWindowInsetTop: Int = 0
    private var lastWindowInsetBottom: Int = 0

    init {
        systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        paint.style = Paint.Style.FILL
        navigationBarDividerHeight = resources.getDimension(R.dimen.navigation_bar_divider_height)
        navigationBarDividerColor = ContextCompat.getColor(context, R.color.design_core_ui_divider)

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
            isLightNavigationBar = ta.getBoolean(R.styleable.FitsSystemWindowsFrameLayout_isLightNavigationBar, false)
            if (isLightNavigationBar) {
                SystemUiVisibilityUtils.setLightNavigationBar(this)
            }
            showNavigationBarDivider = ta.getBoolean(R.styleable.FitsSystemWindowsFrameLayout_showNavigationBarDivider, isLightNavigationBar)
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

        val shouldFallbackToDarkStatusBar = Build.VERSION.SDK_INT < Build.VERSION_CODES.M && isLightStatusBar
        val statusBarColor = if (shouldFallbackToDarkStatusBar) Color.BLACK else this.statusBarColor
        drawRect(canvas, statusBarColor, 0, lastWindowInsetTop)

        val shouldFallbackToDarkNavigationBar = Build.VERSION.SDK_INT < Build.VERSION_CODES.O && isLightNavigationBar
        val navigationBarColor = if (shouldFallbackToDarkNavigationBar) Color.BLACK else this.navigationBarColor
        val navigationBarDividerColor = if (shouldFallbackToDarkNavigationBar) Color.TRANSPARENT else this.navigationBarDividerColor
        val navigationBarTop = (height - lastWindowInsetBottom).toFloat()
        drawRect(canvas, navigationBarColor, navigationBarTop, height.toFloat())
        if (showNavigationBarDivider) {
            drawRect(canvas, navigationBarDividerColor, navigationBarTop, navigationBarTop + navigationBarDividerHeight)
        }
    }

    private fun drawRect(canvas: Canvas, @ColorInt color: Int, top: Int, bottom: Int) {
        drawRect(canvas, color, top.toFloat(), bottom.toFloat())
    }

    private fun drawRect(canvas: Canvas, @ColorInt color: Int, top: Float, bottom: Float) {
        paint.color = color
        canvas.drawRect(0f, top, width.toFloat(), bottom, paint)
    }
}