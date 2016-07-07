package com.learning.english.simple.adapters

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.learning.english.simple.R
import com.learning.english.simple.fragments.WebViewFragment

class LessonAdapter(passedActivity: Activity, passedLessonsNames: List<String>, passedLessonsFiles: List<String>) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {
    var lessonsNames = passedLessonsNames
    var lessonsFiles = passedLessonsFiles
    var activity = passedActivity

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.lesson_item, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder?, position: Int) {
        holder!!.name.text = lessonsNames[position].toString()
        holder.name.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(WebViewFragment.FILE_NAME_KEY, lessonsFiles[position])
            val webViewFragment = WebViewFragment()
            webViewFragment.arguments = bundle
            activity.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, webViewFragment)
                    .commitAllowingStateLoss()
        }
    }

    override fun getItemCount(): Int {
        return lessonsNames.size
    }

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView

        init {
            name = itemView.findViewById(R.id.lesson_item_name) as TextView
        }
    }

}