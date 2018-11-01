package com.example.alockwood.windowinsetsplayground

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.drawer_view)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            navigationView.setCheckedItem(R.id.home)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId

            if (id == R.id.home) {
                replaceFragment(HomeFragment())
            } else if (id == R.id.promos) {
                replaceFragment(PromosFragment())
            } else if (id == R.id.donate) {
                replaceFragment(DonateFragment())
            } else if (id == R.id.collapsing_toolbar) {
                replaceFragment(CollapsingToolbarFragment())
            } else if (id == R.id.toolbar) {
                replaceFragment(ToolbarFragment())
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val profilePhoto = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.profile_photo)
        Glide.with(this)
                .load(Constants.PROFILE_PHOTO_URL)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.circleCropTransform())
                .into(profilePhoto)

        val coverPhoto = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.cover_photo)
        Glide.with(this)
                .load(Constants.COVER_PHOTO_URL)
                .apply(RequestOptions.centerCropTransform())
                .into(coverPhoto)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit()
    }

    override fun onBackPressed() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
