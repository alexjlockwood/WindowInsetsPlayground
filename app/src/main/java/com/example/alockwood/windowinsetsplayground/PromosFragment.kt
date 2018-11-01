package com.example.alockwood.windowinsetsplayground

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class PromosFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_promos, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.promos)

        return view
    }
}
