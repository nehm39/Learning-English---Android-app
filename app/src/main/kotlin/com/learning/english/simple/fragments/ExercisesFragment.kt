package com.learning.english.simple.fragments


import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView

import com.learning.english.simple.R
import java.security.SecureRandom

class ExercisesFragment : Fragment() {
    var wordImage : ImageView? = null
    var etxtWord : EditText? = null
    var etxtInputLayout : TextInputLayout? = null
    var random : SecureRandom? = null
    var currentWord = ""
    var imagesList : List<String>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_exercises, container, false)
        random = SecureRandom()
        wordImage = fragmentView.findViewById(R.id.exercises_image_view) as ImageView
        etxtWord = fragmentView.findViewById(R.id.exercises_etxt_word) as EditText
        etxtInputLayout = fragmentView.findViewById(R.id.exercises_text_input_layout) as TextInputLayout
        imagesList = resources.getStringArray(R.array.exercises_names).asList()
        getRandomImage()
        return fragmentView
    }

    fun getRandomImage() {
        val randomPosition = random!!.nextInt(imagesList!!.size)
        currentWord = imagesList!![randomPosition]
        val resourceImage = activity.resources.getIdentifier(currentWord, "drawable", activity.packageName)
        wordImage!!.setImageResource(resourceImage)
    }
}
