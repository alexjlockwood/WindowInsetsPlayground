package com.example.alockwood.windowinsetsplayground.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowInsets;

public class ExtendedCoordinatorLayout extends CoordinatorLayout {

  public ExtendedCoordinatorLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public WindowInsets dispatchApplyWindowInsets(WindowInsets before) {
    Utils.logInsets("===> CoordinatorLayout", before);
    final WindowInsets after = super.dispatchApplyWindowInsets(before);
    Utils.logInsets("<=== CoordinatorLayout", before);
    return after;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Log.i("==== CoordinatorLayout", "[0, 0, " + w + ", " + h + "]");
  }
}
