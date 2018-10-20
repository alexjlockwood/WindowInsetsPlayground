package com.example.alockwood.windowinsetsplayground.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowInsets;

import com.google.android.gms.maps.MapView;

public class ExtendedMapView extends MapView {

  public ExtendedMapView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
  }

  @Override
  public WindowInsets dispatchApplyWindowInsets(WindowInsets before) {
    Utils.logInsets("===> MapView", before);
    final WindowInsets after = super.dispatchApplyWindowInsets(before);
    Utils.logInsets("<=== MapView", before);
    return after;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Log.i("==== MapView", "[0, 0, " + w + ", " + h + "]");
  }
}
