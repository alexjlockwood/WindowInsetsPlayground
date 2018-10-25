package com.example.alockwood.windowinsetsplayground;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

public class CustomDrawerLayout extends DrawerLayout {

  private View contentView;
  private CustomNavigationView drawerView;
  private View statusBarBackgroundView;

  @Nullable private WindowInsets lastWindowInsets;
  private boolean shouldDrawContentUnderStatusBar;

  public CustomDrawerLayout(@NonNull Context context) {
    this(context, null);
  }

  public CustomDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    if (getFitsSystemWindows()) {
      throw new IllegalStateException("This class must set android:fitsSystemWindows=\"false\"");
    }

    setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    setOnApplyWindowInsetsListener(
        (v, insets) -> {
          lastWindowInsets = insets;
          final ViewGroup.LayoutParams lp = statusBarBackgroundView.getLayoutParams();
          lp.height = insets.getSystemWindowInsetTop();
          statusBarBackgroundView.setLayoutParams(lp);
          requestLayout();
          return insets.consumeSystemWindowInsets();
        });
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    contentView = ViewCompat.requireViewById(this, R.id.content_view);
    drawerView = ViewCompat.requireViewById(this, R.id.drawer_view);
    statusBarBackgroundView = ViewCompat.requireViewById(this, R.id.status_bar_background_view);
  }

  @SuppressLint("RtlHardcoded")
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if (lastWindowInsets == null) {
      return;
    }

    final int leftInset = lastWindowInsets.getSystemWindowInsetLeft();
    final int topInset = lastWindowInsets.getSystemWindowInsetTop();
    final int rightInset = lastWindowInsets.getSystemWindowInsetRight();
    final int bottomInset = lastWindowInsets.getSystemWindowInsetBottom();

    final int drawerViewGravity =
        GravityCompat.getAbsoluteGravity(
            ((DrawerLayout.LayoutParams) drawerView.getLayoutParams()).gravity,
            ViewCompat.getLayoutDirection(this));
    final WindowInsets drawerViewInsets =
        lastWindowInsets.replaceSystemWindowInsets(
            drawerViewGravity == Gravity.RIGHT ? 0 : leftInset,
            topInset,
            drawerViewGravity == Gravity.LEFT ? 0 : rightInset,
            bottomInset);
    drawerView.dispatchApplyWindowInsets(drawerViewInsets);

    final MarginLayoutParams lp = (MarginLayoutParams) contentView.getLayoutParams();
    lp.leftMargin = leftInset;
    lp.topMargin = shouldDrawContentUnderStatusBar ? 0 : topInset;
    lp.rightMargin = rightInset;
    lp.bottomMargin = bottomInset;

    if (shouldDrawContentUnderStatusBar) {
      contentView.dispatchApplyWindowInsets(lastWindowInsets);
    } else {
      final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
      final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
      final int contentWidthSpec =
          MeasureSpec.makeMeasureSpec(
              widthSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY);
      final int contentHeightSpec =
          MeasureSpec.makeMeasureSpec(
              heightSize - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY);

      // TODO: seems kinda hacky to directly measure another view group's child...
      contentView.measure(contentWidthSpec, contentHeightSpec);
    }
  }

  @Nullable
  @Override
  public Drawable getStatusBarBackgroundDrawable() {
    return statusBarBackgroundView.getBackground();
  }

  @Override
  public void setStatusBarBackground(@ColorRes int resId) {
    setStatusBarBackground(resId == 0 ? null : ContextCompat.getDrawable(getContext(), resId));
  }

  @Override
  public void setStatusBarBackgroundColor(@ColorInt int color) {
    setStatusBarBackground(new ColorDrawable(color));
  }

  @Override
  public void setStatusBarBackground(@Nullable Drawable drawable) {
    statusBarBackgroundView.setBackground(drawable);
  }

  public void setShouldDrawContentUnderStatusBar(boolean shouldDrawContentUnderStatusBar) {
    if (this.shouldDrawContentUnderStatusBar != shouldDrawContentUnderStatusBar) {
      this.shouldDrawContentUnderStatusBar = shouldDrawContentUnderStatusBar;
      requestApplyInsets();
    }
  }

  public void setShouldUseLightStatusBar(boolean shouldUseLightStatusBar) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      int flags = getSystemUiVisibility();
      if (shouldUseLightStatusBar) {
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
      } else {
        flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
      }
      setSystemUiVisibility(flags);

      drawerView.setForegroundInset(
          shouldUseLightStatusBar
              ? R.color.design_core_ui_white_alpha50
              : R.color.design_core_ui_black_alpha25);
    }
  }
}
