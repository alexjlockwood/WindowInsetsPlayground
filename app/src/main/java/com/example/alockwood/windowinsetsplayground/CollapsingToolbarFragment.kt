package com.example.alockwood.windowinsetsplayground

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.bumptech.glide.Glide

class CollapsingToolbarFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_collapsing_toolbar, container, false)

        val collapsingToolbar = view.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        collapsingToolbar.title = getString(R.string.collapsing_toolbar)

        val profilePhoto = view.findViewById<ImageView>(R.id.backdrop_photo)
        Glide.with(this).load(Constants.COVER_PHOTO_URL).into(profilePhoto)

        return view
    }
}
