package com.example.alockwood.windowinsetsplayground;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends BaseFragment implements OnMapReadyCallback {

  private static final String MAP_VIEW_BUNDLE_KEY = "map_view_bundle_key";

  private MapView mapView;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_home, container, false);
    mapView = view.findViewById(R.id.map_view);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    Bundle mapViewBundle = null;
    if (savedInstanceState != null) {
      mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
    }
    mapView.onCreate(mapViewBundle);
    mapView.getMapAsync(this);
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
    if (mapViewBundle == null) {
      mapViewBundle = new Bundle();
      outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
    }
    mapView.onSaveInstanceState(mapViewBundle);
  }

  @Override
  public void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
  }

  @Override
  protected int getStatusBarBackground() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        ? R.color.design_core_ui_white_alpha70
        : R.color.design_core_ui_black_alpha40;
  }

  @Override
  protected boolean shouldUseLightStatusBar() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }

  @Override
  protected boolean shouldDrawContentUnderStatusBar() {
    return true;
  }
}
