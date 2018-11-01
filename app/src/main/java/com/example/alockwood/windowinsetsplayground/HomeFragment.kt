package com.example.alockwood.windowinsetsplayground

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        mapView = view.findViewById(R.id.map_view)

        val statusBarBackgroundView: View = view.findViewById(R.id.status_bar_background_view)
        statusBarBackgroundView.setBackgroundResource(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    R.color.design_core_ui_white_alpha70
                } else {
                    R.color.design_core_ui_black_alpha40
                })

        view.setOnApplyWindowInsetsListener { v, insets ->
            val mapLayoutParams = mapView.layoutParams as ViewGroup.MarginLayoutParams
            mapLayoutParams.leftMargin = insets.systemWindowInsetLeft
            mapLayoutParams.rightMargin = insets.systemWindowInsetRight
            mapLayoutParams.bottomMargin = insets.systemWindowInsetBottom
            mapView.layoutParams = mapLayoutParams

            val statusBarLayoutParams = statusBarBackgroundView.layoutParams
            statusBarLayoutParams.height = insets.systemWindowInsetTop
            statusBarBackgroundView.layoutParams = statusBarLayoutParams

            insets.consumeSystemWindowInsets()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(MarkerOptions().position(LatLng(0.0, 0.0)).title("Marker"))
    }

    companion object {

        private val MAP_VIEW_BUNDLE_KEY = "map_view_bundle_key"
    }
}
