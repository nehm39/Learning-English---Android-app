package com.learning.english.simple.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.english.simple.R
import com.learning.english.simple.activity.MainActivity
import com.learning.english.simple.adapters.LessonAdapter
import com.learning.english.simple.utils.Utils

class LessonsListFragment : Fragment() {
    var fragmentView : View? = null
    var recyclerView : RecyclerView? = null

    companion object {
        const val LESSON_TYPE_KEY = "LESSON_TYPE_KEY"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater!!.inflate(R.layout.fragment_lessons_list, container, false)
        recyclerView = fragmentView!!.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView!!.hasFixedSize()
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager

        val lessonsType : Int = arguments[LESSON_TYPE_KEY] as Int
        val lessonsNames : List<String>
        val lessonsFiles : List<String>
        if (lessonsType == Utils.LESSON_TYPE_TEXT) {
            activity.title = resources.getString(R.string.drawer_menu_lessons)
            lessonsNames = resources.getStringArray(R.array.text_lessons_names).asList()
            lessonsFiles = resources.getStringArray(R.array.text_lessons_names_files).asList()
            val lessonAdapter = LessonAdapter(activity as MainActivity, lessonsType, lessonsNames, lessonsFiles)
            recyclerView!!.adapter = lessonAdapter
        } else if (lessonsType == Utils.LESSON_TYPE_VIDEO) {
            activity.title = resources.getString(R.string.drawer_menu_video)
            lessonsNames = resources.getStringArray(R.array.youtube_lessons_names).asList()
            lessonsFiles = resources.getStringArray(R.array.youtube_lessons_names_files).asList()
            val lessonAdapter = LessonAdapter(activity as MainActivity, lessonsType, lessonsNames, lessonsFiles)
            recyclerView!!.adapter = lessonAdapter
        }
        return fragmentView
    }

}
