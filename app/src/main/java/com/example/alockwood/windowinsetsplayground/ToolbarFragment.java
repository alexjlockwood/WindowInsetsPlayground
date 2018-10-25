package com.example.alockwood.windowinsetsplayground;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ToolbarFragment extends BaseFragment {

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_toolbar, container, false);

    final Toolbar toolbar = view.findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.toolbar);

    return view;
  }

  @Override
  protected int getStatusBarBackground() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        ? R.color.design_core_ui_white
        : R.color.design_core_ui_black_alpha40;
  }

  @Override
  protected boolean shouldUseLightStatusBar() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }

  @Override
  protected boolean shouldDrawContentUnderStatusBar() {
    return false;
  }
}
