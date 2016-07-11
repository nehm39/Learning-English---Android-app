package com.learning.english.simple.fragments


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.learning.english.simple.R
import com.learning.english.simple.utils.Utils
import java.security.SecureRandom

class ExercisesFragment : Fragment() {
    var wordImage: ImageView? = null
    var etxtWord: EditText? = null
    var etxtInputLayout: TextInputLayout? = null
    var btnCheck: ImageButton? = null
    var random: SecureRandom? = null
    var currentWord = ""
    var imagesList: List<String>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_exercises, container, false)
        random = SecureRandom()
        wordImage = fragmentView.findViewById(R.id.exercises_image_view) as ImageView
        etxtWord = fragmentView.findViewById(R.id.exercises_etxt_word) as EditText
        etxtInputLayout = fragmentView.findViewById(R.id.exercises_text_input_layout) as TextInputLayout
        btnCheck = fragmentView.findViewById(R.id.exercises_img_btn) as ImageButton
        imagesList = resources.getStringArray(R.array.exercises_names).asList()
        btnCheck!!.setOnClickListener {
            if (validateTextInput()) {
                Utils.showToast(activity, resources.getString(R.string.exercises_correct_answer))
                getRandomImage()
            }
        }
        getRandomImage()
        return fragmentView
    }

    fun validateTextInput(): Boolean {
        if (etxtWord!!.text.trim().isEmpty()) {
            etxtInputLayout!!.error = resources.getString(R.string.empty_input_field)
            return false
        } else if (etxtWord!!.text.toString() != currentWord) {
            etxtInputLayout!!.error = resources.getString(R.string.exercises_bad_answer)
            return false
        } else {
            etxtInputLayout!!.isErrorEnabled = false;
            return true
        }
    }

    //TODO: needs to be on bg thread
    //RxJava or AsyncTask?
    fun getRandomImage() {
        val randomPosition = random!!.nextInt(imagesList!!.size)
        currentWord = imagesList!![randomPosition]
        val resourceImage = Drawable.createFromResourceStream(resources, TypedValue(), resources.getAssets().open("exercises/" + currentWord + ".jpg"), null)
        wordImage!!.setImageDrawable(resourceImage)
        etxtWord!!.text = SpannableStringBuilder("")
    }
}
