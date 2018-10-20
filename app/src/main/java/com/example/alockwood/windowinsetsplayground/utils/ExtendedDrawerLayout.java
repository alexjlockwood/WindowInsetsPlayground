package com.example.alockwood.windowinsetsplayground.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowInsets;

public class ExtendedDrawerLayout extends DrawerLayout {

  public ExtendedDrawerLayout(@NonNull Context context) {
    this(context, null);
  }

  public ExtendedDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ExtendedDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public WindowInsets dispatchApplyWindowInsets(WindowInsets before) {
    Utils.logInsets("===> DrawerLayout", before);
    final WindowInsets after = super.dispatchApplyWindowInsets(before);
    Utils.logInsets("<=== DrawerLayout", before);
    return after;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Log.i("==== DrawerLayout", "[0, 0, " + w + ", " + h + "]");
  }
}
