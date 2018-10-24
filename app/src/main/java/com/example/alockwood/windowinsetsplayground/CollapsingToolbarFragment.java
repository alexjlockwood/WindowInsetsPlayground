package com.example.alockwood.windowinsetsplayground;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CollapsingToolbarFragment extends BaseFragment {

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_collapsing_toolbar, container, false);

    final CollapsingToolbarLayout collapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
    collapsingToolbar.setTitle(getString(R.string.collapsing_toolbar));

    final ImageView profilePhoto = view.findViewById(R.id.backdrop_photo);
    Glide.with(this)
        .load(Constants.COVER_PHOTO_URL)
        .into(profilePhoto);

    return view;
  }

  @Override
  protected void onSetupDrawerLayout(CustomDrawerLayout drawerLayout) {
    drawerLayout.setStatusBarBackgroundColor(Color.TRANSPARENT);
    drawerLayout.setShouldDrawChildrenUnderStatusBar(true);
    drawerLayout.setShouldUseLightStatusBar(false);
  }
}
