package com.learning.english.simple.fragments


import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.learning.english.simple.R

class StartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_start, container, false)
        val txtWelcome = fragmentView.findViewById(R.id.start_welcome_text) as TextView
        val txtInfo = fragmentView.findViewById(R.id.start_info_text) as TextView
        val typeface = Typeface.createFromAsset(activity.assets, "DroidSerif.ttf")
        txtWelcome.setTypeface(typeface)
        txtInfo.setTypeface(typeface)
        return fragmentView
    }

}
