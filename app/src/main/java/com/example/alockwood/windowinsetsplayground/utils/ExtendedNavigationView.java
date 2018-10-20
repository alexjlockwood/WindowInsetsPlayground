package com.example.alockwood.windowinsetsplayground.utils;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowInsets;

public class ExtendedNavigationView extends NavigationView {

  public ExtendedNavigationView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public WindowInsets dispatchApplyWindowInsets(WindowInsets before) {
    Utils.logInsets("===> NavigationView", before);
    final WindowInsets after = super.dispatchApplyWindowInsets(before);
    Utils.logInsets("<=== NavigationView", before);
    return after;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Log.i("==== NavigationView", "[0, 0, " + w + ", " + h + "]");
  }
}
