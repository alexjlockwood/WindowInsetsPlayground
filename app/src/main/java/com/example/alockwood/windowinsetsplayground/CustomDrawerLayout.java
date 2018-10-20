package com.example.alockwood.windowinsetsplayground;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;

import com.example.alockwood.windowinsetsplayground.utils.ExtendedDrawerLayout;

public class CustomDrawerLayout extends ExtendedDrawerLayout {

  private View contentView;
  private View drawerView;

  @Nullable private WindowInsets lastWindowInsets;
  private boolean shouldConsumeContentViewInsets;
  private boolean shouldDrawStatusBarBackground;
  @Nullable private Drawable statusBarBackground;

  public CustomDrawerLayout(@NonNull Context context) {
    this(context, null);
  }

  public CustomDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    contentView = findViewById(R.id.content_view);
    drawerView = findViewById(R.id.drawer_view);

    setOnApplyWindowInsetsListener(
        (v, insets) -> {
          lastWindowInsets = insets;
          shouldDrawStatusBarBackground = insets.getSystemWindowInsetTop() > 0;
          setWillNotDraw(!shouldDrawStatusBarBackground && getBackground() == null);
          requestLayout();
          return insets.consumeSystemWindowInsets();
        });
  }

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

    // TODO: support RTL
    final WindowInsets drawerViewInsets =
        lastWindowInsets.replaceSystemWindowInsets(leftInset, topInset, 0, bottomInset);
    drawerView.dispatchApplyWindowInsets(drawerViewInsets);

    WindowInsets contentViewInsets = lastWindowInsets;
    if (shouldConsumeContentViewInsets) {
      final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
      final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
      final MarginLayoutParams lp = (MarginLayoutParams) contentView.getLayoutParams();
      lp.leftMargin = leftInset;
      lp.topMargin = topInset;
      lp.rightMargin = rightInset;
      lp.bottomMargin = bottomInset;
      final int contentWidthSpec =
          MeasureSpec.makeMeasureSpec(
              widthSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY);
      final int contentHeightSpec =
          MeasureSpec.makeMeasureSpec(
              heightSize - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY);
      contentView.measure(contentWidthSpec, contentHeightSpec);
    } else {
      contentView.dispatchApplyWindowInsets(contentViewInsets);
    }
  }

  @Override
  public void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    if (shouldDrawStatusBarBackground && statusBarBackground != null) {
      final int topInset =
          lastWindowInsets == null ? 0 : lastWindowInsets.getSystemWindowInsetTop();
      if (topInset > 0) {
        statusBarBackground.setBounds(0, 0, getWidth(), topInset);
        statusBarBackground.draw(canvas);
      }
    }
  }
}
