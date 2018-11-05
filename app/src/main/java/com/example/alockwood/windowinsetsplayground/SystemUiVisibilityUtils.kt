package com.example.alockwood.windowinsetsplayground

import android.os.Build
import android.view.View

object SystemUiVisibilityUtils {

    @JvmStatic
    fun setLightStatusBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setSystemUiVisibilityFlag(view, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }

    @JvmStatic
    fun clearLightStatusBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            clearSystemUiVisibilityFlag(view, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }

    @JvmStatic
    fun setLightNavigationBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setSystemUiVisibilityFlag(view, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }
    }

    @JvmStatic
    fun clearLightNavigationBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            clearSystemUiVisibilityFlag(view, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }
    }

    private fun setSystemUiVisibilityFlag(view: View, flag: Int) {
        var flags = view.systemUiVisibility
        flags = flags or flag
        view.systemUiVisibility = flags
    }

    private fun clearSystemUiVisibilityFlag(view: View, flag: Int) {
        var flags = view.systemUiVisibility
        flags = flags and flag.inv()
        view.systemUiVisibility = flags
    }
}