package com.example.alockwood.windowinsetsplayground

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets

/**
 * A [DrawerLayout] with customized window inset behavior.
 */
open class FitsSystemWindowsDrawerLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : DrawerLayout(context, attrs, defStyleAttr) {

    private val drawerView: View
    private val contentView: View

    private var lastWindowInsets: WindowInsets? = null

    init {
        if (fitsSystemWindows) {
            // This is required because otherwise the super class will try to apply its own
            // custom window insets logic.
            throw IllegalStateException("This class must set android:fitsSystemWindows=\"false\"")
        }

        LayoutInflater.from(context).inflate(R.layout.fits_system_windows_drawer_layout_contents, this, true)
        contentView = ViewCompat.requireViewById(this, R.id.content_view)
        drawerView = ViewCompat.requireViewById(this, R.id.drawer_view)

        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        setOnApplyWindowInsetsListener { _, insets ->
            lastWindowInsets = insets
            requestLayout()
            insets.consumeSystemWindowInsets()
        }
    }

    @SuppressLint("RtlHardcoded")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val insets = this.lastWindowInsets
        if (insets != null) {
            val leftInset = insets.systemWindowInsetLeft
            val topInset = insets.systemWindowInsetTop
            val rightInset = insets.systemWindowInsetRight
            val bottomInset = insets.systemWindowInsetBottom

            // Dispatch the insets to the drawer view (this behavior is identical to how it is
            // implemented in the super class).
            val drawerViewParams = drawerView.layoutParams as DrawerLayout.LayoutParams
            val drawerViewGravity = GravityCompat.getAbsoluteGravity(
                    drawerViewParams.gravity, ViewCompat.getLayoutDirection(this)
            )
            drawerView.dispatchApplyWindowInsets(
                    insets.replaceSystemWindowInsets(
                            if (drawerViewGravity == Gravity.RIGHT) 0 else leftInset,
                            topInset,
                            if (drawerViewGravity == Gravity.LEFT) 0 else rightInset,
                            bottomInset
                    )
            )

            // Dispatch the insets so that a child view can potentially consume them.
            contentView.dispatchApplyWindowInsets(insets)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
