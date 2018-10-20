package com.example.alockwood.windowinsetsplayground;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

  protected CustomDrawerLayout getDrawerLayout() {
    return ((MainActivity) getContext()).getDrawerLayout();
  }
}
