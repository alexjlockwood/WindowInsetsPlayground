package com.example.alockwood.windowinsetsplayground;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    final CustomDrawerLayout drawerLayout =
        ActivityCompat.requireViewById((MainActivity) requireContext(), R.id.drawer_layout);
    drawerLayout.setStatusBarBackground(getStatusBarBackground());
    drawerLayout.setShouldDrawContentUnderStatusBar(shouldDrawContentUnderStatusBar());
    drawerLayout.setShouldUseLightStatusBar(shouldUseLightStatusBar());
  }

  @ColorRes
  protected abstract int getStatusBarBackground();

  protected abstract boolean shouldUseLightStatusBar();

  protected abstract boolean shouldDrawContentUnderStatusBar();
}
