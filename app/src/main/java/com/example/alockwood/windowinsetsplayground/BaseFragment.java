package com.example.alockwood.windowinsetsplayground;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    onSetupDrawerLayout(((MainActivity) getContext()).findViewById(R.id.drawer_layout));
  }

  protected abstract void onSetupDrawerLayout(CustomDrawerLayout drawerLayout);
}
