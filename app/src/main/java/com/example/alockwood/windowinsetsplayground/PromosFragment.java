package com.example.alockwood.windowinsetsplayground;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PromosFragment extends BaseFragment {

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_promos, container, false);

    final Toolbar toolbar = view.findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.promos);

    return view;
  }

  @Override
  protected int getStatusBarBackground() {
    return R.color.design_core_ui_purple80;
  }

  @Override
  protected boolean shouldUseLightStatusBar() {
    return false;
  }

  @Override
  protected boolean shouldDrawContentUnderStatusBar() {
    return false;
  }
}
