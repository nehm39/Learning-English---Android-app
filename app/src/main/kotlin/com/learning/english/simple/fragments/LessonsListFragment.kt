package com.learning.english.simple.fragments


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.english.simple.R
import com.learning.english.simple.adapters.LessonAdapter

class LessonsListFragment : Fragment() {
    var fragmentView : View? = null
    var recyclerView : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater!!.inflate(R.layout.fragment_lessons_list, container, false)
        recyclerView = fragmentView!!.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView!!.hasFixedSize()
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager

        val lessonsNames : List<String> = resources.getStringArray(R.array.lessons_names).asList()
        val lessonsFiles : List<String> = resources.getStringArray(R.array.lessons_names_files).asList()
        val lessonAdapter = LessonAdapter(activity, lessonsNames, lessonsFiles)
        recyclerView!!.adapter = lessonAdapter
        return fragmentView
    }

}
