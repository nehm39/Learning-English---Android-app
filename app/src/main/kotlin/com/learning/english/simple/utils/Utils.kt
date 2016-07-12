package com.learning.english.simple.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.speech.RecognizerIntent
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.learning.english.simple.R

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

    fun isInternetConnectionAvailable(activity: Activity) : Boolean {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo != null) {
            return true
        } else {
            showToast(activity, activity.resources.getString(R.string.connection_error))
            return false
        }
    }

    fun startVoiceInput(activity: Activity, requestCode: Int, language: String) {
        val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
        try {
            activity.startActivityForResult(i, requestCode)
        } catch (e: Exception) {
            Utils.showToast(activity, activity.resources.getString(R.string.voice_input_error))
        }
    }

    fun getVoiceInput(requestCode: Int, resultCode: Int, data: Intent?, correctRequestCode: Int) : String? {
        if (requestCode == correctRequestCode && resultCode == Activity.RESULT_OK && data != null) {
            val recordedText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (recordedText.size > 0) {
                return recordedText[0]
            }
        }
        return null
    }
}