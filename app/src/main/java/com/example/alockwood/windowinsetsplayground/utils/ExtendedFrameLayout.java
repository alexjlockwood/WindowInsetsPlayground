package com.example.alockwood.windowinsetsplayground.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowInsets;
import android.widget.FrameLayout;

public class ExtendedFrameLayout extends FrameLayout {

  public ExtendedFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public WindowInsets dispatchApplyWindowInsets(WindowInsets before) {
    Utils.logInsets("===> FrameLayout", before);
    final WindowInsets after = super.dispatchApplyWindowInsets(before);
    Utils.logInsets("<=== FrameLayout", before);
    return after;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Log.i("==== FrameLayout", "[0, 0, " + w + ", " + h + "]");
  }
}
