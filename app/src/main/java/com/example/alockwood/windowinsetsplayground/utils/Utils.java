package com.example.alockwood.windowinsetsplayground.utils;

import android.util.Log;
import android.view.WindowInsets;

public final class Utils {

  public static void logInsets(String tag, WindowInsets insets) {
    Log.i(
        tag,
        "["
            + insets.getSystemWindowInsetLeft()
            + ", "
            + insets.getSystemWindowInsetTop()
            + ", "
            + insets.getSystemWindowInsetRight()
            + ", "
            + insets.getSystemWindowInsetBottom()
            + "]");
  }

  private Utils() {}
}
