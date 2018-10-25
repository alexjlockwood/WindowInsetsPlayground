package com.example.alockwood.windowinsetsplayground;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.reflect.Field;

public class CustomNavigationView extends NavigationView {
  private static final String TAG = "CustomNavigationView";

  private static Field insetForegroundField;
  private static boolean insetForegroundFieldFetched;

  public CustomNavigationView(Context context) {
    this(context, null);
  }

  public CustomNavigationView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void setForegroundInset(@ColorRes int resId) {
    setForegroundInset(resId == 0 ? null : ContextCompat.getDrawable(getContext(), resId));
  }

  public void setForegroundInsetColor(@ColorInt int color) {
    setForegroundInset(new ColorDrawable(color));
  }

  public void setForegroundInset(@Nullable Drawable drawable) {
    if (!insetForegroundFieldFetched) {
      try {
        insetForegroundField = ScrimInsetsFrameLayout.class.getDeclaredField("insetForeground");
        insetForegroundField.setAccessible(true);
      } catch (NoSuchFieldException e) {
        Log.i(TAG, "Failed to access insetForeground field by reflection");
      }
      insetForegroundFieldFetched = true;
    }
    if (insetForegroundField != null) {
      try {
        insetForegroundField.set(this, drawable);
      } catch (IllegalAccessException e) {
        Log.i(TAG, "Failed to set insetForeground field by reflection");
      }
    }
    invalidate();
  }
}
