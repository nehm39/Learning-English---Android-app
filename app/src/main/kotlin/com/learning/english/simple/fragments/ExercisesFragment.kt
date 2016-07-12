package com.learning.english.simple.fragments


import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.learning.english.simple.Observable.ExerciseObservable
import com.learning.english.simple.R
import com.learning.english.simple.utils.Utils
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.security.SecureRandom

class ExercisesFragment : Fragment() {
    var wordImage: ImageView? = null
    var etxtWord: EditText? = null
    var etxtInputLayout: TextInputLayout? = null
    var btnCheck: ImageButton? = null
    var random: SecureRandom? = null
    var currentWord = ""
    var filesList: List<String>? = null

    var imageLock = false

    val VOICE_REQUEST_OK = 7831

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_exercises, container, false)
        activity.title = resources.getString(R.string.drawer_menu_exercises)
        random = SecureRandom()
        wordImage = fragmentView.findViewById(R.id.exercises_image_view) as ImageView
        etxtWord = fragmentView.findViewById(R.id.exercises_etxt_word) as EditText
        etxtInputLayout = fragmentView.findViewById(R.id.exercises_text_input_layout) as TextInputLayout
        btnCheck = fragmentView.findViewById(R.id.exercises_img_btn) as ImageButton
        filesList = resources.getAssets().list("exercises").asList()
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

    fun getRandomImage() {
        if (!imageLock) {
            imageLock = true
            val randomPosition = random!!.nextInt(filesList!!.size)
            currentWord = filesList!![randomPosition].substring(0, filesList!![randomPosition].indexOf(".jpg"))
            ExerciseObservable.loadExerciseImage(resources, currentWord)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<Drawable>() {
                        override fun onCompleted() {
                        }

                        override fun onNext(drawable: Drawable?) {
                            wordImage!!.setImageDrawable(drawable)
                            etxtWord!!.text = SpannableStringBuilder("")
                            imageLock = false
                        }

                        override fun onError(e: Throwable?) {
                        }
                    })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exercise_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exercise_menu_skip -> {
                getRandomImage()
            }
            R.id.exercise_menu_voice_input -> {
                Utils.startVoiceInput(activity, VOICE_REQUEST_OK, "pl-PL")
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val voiceInput = Utils.getVoiceInput(requestCode, resultCode, data, VOICE_REQUEST_OK)
        if (voiceInput != null) {
            etxtWord!!.text = SpannableStringBuilder(voiceInput)
        }
    }
}
