package com.example.alockwood.windowinsetsplayground.utils;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowInsets;

public class ExtendedAppBarLayout extends AppBarLayout {

  public ExtendedAppBarLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public WindowInsets dispatchApplyWindowInsets(WindowInsets before) {
    Utils.logInsets("===> AppBarLayout", before);
    final WindowInsets after = super.dispatchApplyWindowInsets(before);
    Utils.logInsets("<=== AppBarLayout", before);
    return after;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Log.i("==== AppBarLayout", "[0, 0, " + w + ", " + h + "]");
  }
}
