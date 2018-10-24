package com.example.alockwood.windowinsetsplayground;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
    final NavigationView navigationView = findViewById(R.id.drawer_view);

    if (savedInstanceState == null) {
      replaceFragment(new HomeFragment());
      navigationView.setCheckedItem(R.id.home);
    }

    navigationView.setNavigationItemSelectedListener(
        menuItem -> {
          final int id = menuItem.getItemId();

          if (id == R.id.home) {
            replaceFragment(new HomeFragment());
          } else if (id == R.id.promos) {
            replaceFragment(new PromosFragment());
          } else if (id == R.id.donate) {
            replaceFragment(new DonateFragment());
          } else if (id == R.id.photo) {
            replaceFragment(new PhotoFragment());
          }

          drawerLayout.closeDrawer(GravityCompat.START);
          return true;
        });

    final ImageView profilePhoto = navigationView.getHeaderView(0).findViewById(R.id.profile_photo);
    Glide.with(this)
        .load(Constants.PROFILE_PHOTO_URL)
        .apply(RequestOptions.circleCropTransform())
        .into(profilePhoto);

    final ImageView coverPhoto = navigationView.getHeaderView(0).findViewById(R.id.cover_photo);
    Glide.with(this)
        .load(Constants.COVER_PHOTO_URL)
        .apply(RequestOptions.centerCropTransform())
        .into(coverPhoto);
  }

  private void replaceFragment(Fragment fragment) {
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.content_view, fragment)
        .commit();
  }

  @Override
  public void onBackPressed() {
    final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.requestApplyInsets();
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }
}
