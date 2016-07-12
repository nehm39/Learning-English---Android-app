package com.learning.english.simple.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.learning.english.simple.R

class InformationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_information, container, false)
        val txtInfo = fragmentView.findViewById(R.id.txt_information) as TextView
        Linkify.addLinks(txtInfo, Linkify.ALL)
        return fragmentView
    }

}
