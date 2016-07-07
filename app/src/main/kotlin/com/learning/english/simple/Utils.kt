package com.learning.english.simple

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

object Utils {
    val GOOGLE_API_KEY = "AIzaSyAeGr6LNPxFm0Rkkbd27Nl36GHDprIZAkU"

    val LESSON_TYPE_VIDEO = 5436
    val LESSON_TYPE_TEXT = 5435

    fun showToast(activity : Activity, text : String) {
        val inflater = activity.layoutInflater
        val layout = inflater!!.inflate(R.layout.toast_view, activity.findViewById(R.id.layout_toast) as ViewGroup?)
        val toastText = layout.findViewById(R.id.txt_toast) as TextView
        toastText.text = text
        val toast = Toast(activity)
        toast.setGravity(Gravity.BOTTOM, 0, 100)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}