package com.learning.english.simple.adapters

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.learning.english.simple.activity.MainActivity
import com.learning.english.simple.R
import com.learning.english.simple.fragments.WebViewFragment
import com.learning.english.simple.fragments.YoutubeFragment
import com.learning.english.simple.utils.Utils

class LessonAdapter(passedActivity: MainActivity, passedLessonsType : Int, passedLessonsNames: List<String>, passedLessonsFiles: List<String>) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {
    var lessonsNames = passedLessonsNames
    var lessonsFiles = passedLessonsFiles
    var activity = passedActivity
    var lessonsType = passedLessonsType

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.lesson_item, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder?, position: Int) {
        val dp : Int = (activity.resources.displayMetrics.density * 16).toInt()
        if (position == 0) {
            holder!!.mainLayout.setPadding(dp, dp, dp, dp)
        } else {
            holder!!.mainLayout.setPadding(dp, 0, dp, dp)
        }
        holder!!.name.text = lessonsNames[position].toString()
        holder.circleText.text = (position + 1).toString()
        holder.name.setOnClickListener {
            val bundle = Bundle()
            if (lessonsType == Utils.LESSON_TYPE_VIDEO) {
                bundle.putString(YoutubeFragment.VIDEO_ID_KEY, lessonsFiles[position])
                val youtubeFragment = YoutubeFragment()
                youtubeFragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, youtubeFragment)
                        .commitAllowingStateLoss()
            } else if (lessonsType == Utils.LESSON_TYPE_TEXT) {
                bundle.putString(WebViewFragment.FILE_NAME_KEY, lessonsFiles[position])
                val webViewFragment = WebViewFragment()
                webViewFragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, webViewFragment)
                        .commitAllowingStateLoss()
            }
        }
    }

    override fun getItemCount(): Int {
        return lessonsNames.size
    }

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView
        internal var circleText: TextView
        internal var mainLayout: LinearLayout

        init {
            name = itemView.findViewById(R.id.lesson_item_name) as TextView
            circleText = itemView.findViewById(R.id.lessons_circle_text) as TextView
            mainLayout = itemView.findViewById(R.id.lessons_item_layout) as LinearLayout
        }
    }

}