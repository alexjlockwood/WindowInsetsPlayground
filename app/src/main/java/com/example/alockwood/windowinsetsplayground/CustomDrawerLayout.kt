package com.example.alockwood.windowinsetsplayground

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
import android.view.*

/**
 * A [DrawerLayout] with customized window inset behavior.
 */
class CustomDrawerLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : DrawerLayout(context, attrs, defStyleAttr) {

    private val drawerView: ScrimInsetsNavigationView
    private val contentView: View
    private val statusBarBackgroundView: View

    private var lastWindowInsets: WindowInsets? = null
    private var shouldDrawContentUnderStatusBar: Boolean = false

    init {
        if (fitsSystemWindows) {
            // This is required because otherwise the superclass will try to apply its own
            // custom window insets logic.
            throw IllegalStateException("This class must set android:fitsSystemWindows=\"false\"")
        }

        LayoutInflater.from(context).inflate(R.layout.drawer_layout_contents, this, true)
        contentView = ViewCompat.requireViewById(this, R.id.content_view)
        statusBarBackgroundView = ViewCompat.requireViewById(this, R.id.status_bar_background_view)
        drawerView = ViewCompat.requireViewById(this, R.id.drawer_view)

        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        setOnApplyWindowInsetsListener { _, insets ->
            lastWindowInsets = insets
            // A custom status bar background view is required for the status bar to potentially
            // draw on top of the main content view.
            val lp = statusBarBackgroundView.layoutParams
            lp.height = insets.systemWindowInsetTop
            statusBarBackgroundView.layoutParams = lp
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

            // This behavior is identical to the behavior used in the super class.
            val drawerViewParams = drawerView.layoutParams as DrawerLayout.LayoutParams
            val drawerViewGravity = GravityCompat.getAbsoluteGravity(
                    drawerViewParams.gravity, ViewCompat.getLayoutDirection(this))
            drawerView.dispatchApplyWindowInsets(
                    insets.replaceSystemWindowInsets(
                            if (drawerViewGravity == Gravity.RIGHT) 0 else leftInset,
                            topInset,
                            if (drawerViewGravity == Gravity.LEFT) 0 else rightInset,
                            bottomInset))

            // If the content should not draw under the status bar, we consume the top inset.
            // If not, we dispatch the insets as-is and let a child view potentially consume them.
            contentView.dispatchApplyWindowInsets(
                    insets.replaceSystemWindowInsets(
                            leftInset, if (shouldDrawContentUnderStatusBar) topInset else 0, rightInset, bottomInset))

            val contentViewParams = contentView.layoutParams as ViewGroup.MarginLayoutParams
            contentViewParams.leftMargin = leftInset
            contentViewParams.topMargin = if (shouldDrawContentUnderStatusBar) 0 else topInset
            contentViewParams.rightMargin = rightInset
            contentViewParams.bottomMargin = bottomInset
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun getStatusBarBackgroundDrawable(): Drawable? {
        return statusBarBackgroundView.background
    }

    override fun setStatusBarBackground(@ColorRes resId: Int) {
        setStatusBarBackground(if (resId == 0) null else ContextCompat.getDrawable(context, resId))
    }

    override fun setStatusBarBackgroundColor(@ColorInt color: Int) {
        setStatusBarBackground(ColorDrawable(color))
    }

    override fun setStatusBarBackground(drawable: Drawable?) {
        statusBarBackgroundView.background = drawable
    }

    fun setShouldDrawContentUnderStatusBar(shouldDrawContentUnderStatusBar: Boolean) {
        if (this.shouldDrawContentUnderStatusBar != shouldDrawContentUnderStatusBar) {
            this.shouldDrawContentUnderStatusBar = shouldDrawContentUnderStatusBar
            requestApplyInsets()
        }
    }

    fun setShouldUseLightStatusBar(shouldUseLightStatusBar: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = systemUiVisibility
            if (shouldUseLightStatusBar) {
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            systemUiVisibility = flags

            drawerView.setScrimInsetResource(
                    if (shouldUseLightStatusBar)
                        R.color.design_core_ui_white_alpha50
                    else
                        R.color.design_core_ui_black_alpha25
            )
        }
    }
}
