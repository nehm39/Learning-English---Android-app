package com.learning.english.simple.fragments


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

import com.learning.english.simple.R

class WebViewFragment : Fragment() {

    companion object {
        const val FILE_NAME_KEY = "FILE_NAME_KEY"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_web_view, container, false)
        val webView = fragmentView.findViewById(R.id.web_view) as WebView
        webView.settings.allowFileAccess = true
        webView.settings.javaScriptEnabled = false
        val fileName = arguments[FILE_NAME_KEY]
        webView.loadUrl("file:///android_asset/" + fileName)
        return fragmentView
    }

}
