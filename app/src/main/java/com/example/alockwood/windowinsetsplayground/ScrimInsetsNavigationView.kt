package com.example.alockwood.windowinsetsplayground

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.WindowInsetsCompat
import android.util.AttributeSet


/**
 * A [NavigationView] that allows for the customization of its scrim inset color.
 *
 * Source code copied from material-components-android: https://go.lyft.com/ScrimInsetsFrameLayout
 */
class ScrimInsetsNavigationView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : NavigationView(context, attrs, defStyleAttr) {

    private val insets = Rect()
    private val tempRect = Rect()
    private var scrimInset: Drawable

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ScrimInsetsNavigationView)
        try {
            val insetForeground = ta.getDrawable(R.styleable.ScrimInsetsNavigationView_insetForeground);
            if (insetForeground !is ColorDrawable || insetForeground.color != Color.TRANSPARENT) {
                throw IllegalStateException("This class must set app:insetForeground=\"@android:color/transparent\"")
            }
        } finally {
            ta.recycle()
        }

        scrimInset = ContextCompat.getDrawable(context, R.color.design_core_ui_black_alpha25)!!
    }

    fun setScrimInsetResource(@DrawableRes resId: Int) {
        scrimInset = ContextCompat.getDrawable(context, resId)!!
        invalidate()
    }

    @SuppressLint("RestrictedApi")
    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.save()
        canvas.translate(scrollX.toFloat(), scrollY.toFloat())

        // Top.
        tempRect.set(0, 0, width, insets.top)
        scrimInset.bounds = tempRect
        scrimInset.draw(canvas)

        // Bottom.
        tempRect.set(0, height - insets.bottom, width, height)
        scrimInset.bounds = tempRect
        scrimInset.draw(canvas)

        // Left.
        tempRect.set(0, insets.top, insets.left, height - insets.bottom)
        scrimInset.bounds = tempRect
        scrimInset.draw(canvas)

        // Right.
        tempRect.set(width - insets.right, insets.top, width, height - insets.bottom)
        scrimInset.bounds = tempRect
        scrimInset.draw(canvas)

        canvas.restore()
    }

    @SuppressLint("RestrictedApi")
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        scrimInset.callback = this
    }

    @SuppressLint("RestrictedApi")
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scrimInset.callback = null
    }

    @SuppressLint("RestrictedApi")
    override fun onInsetsChanged(insets: WindowInsetsCompat) {
        super.onInsetsChanged(insets)
        this.insets.set(
                insets.getSystemWindowInsetLeft(),
                insets.getSystemWindowInsetTop(),
                insets.getSystemWindowInsetRight(),
                insets.getSystemWindowInsetBottom()); }

}
