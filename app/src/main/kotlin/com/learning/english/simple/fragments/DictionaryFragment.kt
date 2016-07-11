package com.learning.english.simple.fragments


import android.app.Activity
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.*
import android.widget.*
import com.learning.english.simple.R
import com.learning.english.simple.api.DictionaryRetrofitSingleton
import com.learning.english.simple.model.DictionaryAudio
import com.learning.english.simple.model.DictionaryDefinition
import com.learning.english.simple.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DictionaryFragment : Fragment() {
    var txtWordName: TextView? = null
    var txtWordDefinition: TextView? = null
    var btnSearch: ImageButton? = null
    var btnAudio: ImageButton? = null
    var etxtSearchedWord: EditText? = null
    var searchedWordTextInput: TextInputLayout? = null

    var resultLayout: RelativeLayout? = null
    var emptyLayout: RelativeLayout? = null
    var progressBar: ProgressBar? = null

    var definitionResponse: Response<List<DictionaryDefinition>>? = null
    var audioResponse: Response<List<DictionaryAudio>>? = null

    var audioLock = false

    var mPlayer: MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_dictionary, container, false)
        txtWordName = fragmentView.findViewById(R.id.dictionary_word_text) as TextView
        txtWordDefinition = fragmentView.findViewById(R.id.dictionary_definitions_text) as TextView
        btnSearch = fragmentView.findViewById(R.id.dictionary_img_btn) as ImageButton
        btnAudio = fragmentView.findViewById(R.id.dictionary_play_audio_btn) as ImageButton
        etxtSearchedWord = fragmentView.findViewById(R.id.etxt_searched_word) as EditText
        resultLayout = fragmentView.findViewById(R.id.dictionary_result_layout) as RelativeLayout
        emptyLayout = fragmentView.findViewById(R.id.dictionary_empty_layout) as RelativeLayout
        progressBar = fragmentView.findViewById(R.id.dictionary_progress_bar) as ProgressBar
        searchedWordTextInput = fragmentView.findViewById(R.id.input_layout_text_to_translate) as TextInputLayout

        mPlayer = MediaPlayer()
        mPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mPlayer!!.setOnCompletionListener {
            mPlayer!!.stop()
            audioLock = false
        }

        btnSearch!!.setOnClickListener {
            if (validateTextInput() && Utils.isInternetConnectionAvailable(activity)) {
                btnSearch!!.isEnabled = false
                progressBar!!.visibility = View.VISIBLE
                emptyLayout!!.visibility = View.GONE
                resultLayout!!.visibility = View.GONE
                audioResponse = null
                definitionResponse = null

                val dictionaryService = DictionaryRetrofitSingleton.client
                val call = dictionaryService.getDefinitions(etxtSearchedWord!!.text.trim().toString().toLowerCase())
                call.enqueue(object : Callback<List<DictionaryDefinition>> {
                    override fun onResponse(call: Call<List<DictionaryDefinition>>?, response: Response<List<DictionaryDefinition>>?) {
                        definitionResponse = response
                        validateResults()
                    }

                    override fun onFailure(call: Call<List<DictionaryDefinition>>?, t: Throwable?) {
                        Utils.showToast(activity, resources.getString(R.string.error_try_again))
                    }

                })

                val callForAudio = dictionaryService.getAudio(etxtSearchedWord!!.text.trim().toString().toLowerCase())
                callForAudio.enqueue(object : Callback<List<DictionaryAudio>> {
                    override fun onResponse(call: Call<List<DictionaryAudio>>?, response: Response<List<DictionaryAudio>>?) {
                        audioResponse = response
                        validateResults()
                    }

                    override fun onFailure(call: Call<List<DictionaryAudio>>?, t: Throwable?) {
                        Utils.showToast(activity, resources.getString(R.string.error_try_again))
                    }

                })
            }
        }

        btnAudio!!.setOnClickListener {
            if (!audioLock && Utils.isInternetConnectionAvailable(activity)) {
                audioLock = true
                try {
                    mPlayer!!.prepare();
                    mPlayer!!.start();
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

        return fragmentView
    }

    fun validateResults() {
        if (audioResponse != null && definitionResponse != null) {
            val audioBody = audioResponse!!.body()
            val definitionBody = definitionResponse!!.body()
            btnSearch!!.isEnabled = true
            progressBar!!.visibility = View.GONE
            if (definitionBody.isEmpty()) {
                emptyLayout!!.visibility = View.VISIBLE
                resultLayout!!.visibility = View.GONE
            } else {
                emptyLayout!!.visibility = View.GONE
                resultLayout!!.visibility = View.VISIBLE
                var definition = ""
                definitionBody.forEachIndexed { i, dictionaryDefinition ->
                    definition = definition + (i + 1) + ". (" + dictionaryDefinition.partOfSpeech + ") " + dictionaryDefinition.text + System.getProperty("line.separator")
                }
                definition = definition.replace("   ", " - ")
                definition = definition.replace("  ", " ")
                txtWordName!!.text = etxtSearchedWord!!.text.toString()
                txtWordDefinition!!.text = definition

                if (audioBody.isEmpty()) {
                    btnAudio!!.visibility = View.GONE
                } else {
                    btnAudio!!.visibility = View.VISIBLE
                    mPlayer!!.reset()
                    mPlayer!!.setDataSource(audioBody[0].fileUrl)
                }
            }
        }
    }

    fun validateTextInput(): Boolean {
        if (etxtSearchedWord!!.text.trim().isEmpty()) {
            searchedWordTextInput!!.error = resources.getString(R.string.empty_input_field)
            return false
        } else {
            searchedWordTextInput!!.isErrorEnabled = false;
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dictionary_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    val VOICE_REQUEST_OK = 7831

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dictionary_voice_input -> {
                val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
                try {
                    startActivityForResult(i, VOICE_REQUEST_OK)
                } catch (e: Exception) {
                    Utils.showToast(activity, resources.getString(R.string.voice_input_error))
                }


            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VOICE_REQUEST_OK && resultCode == Activity.RESULT_OK && data != null) {
            val recordedText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (recordedText.size > 0) {
                etxtSearchedWord!!.text = SpannableStringBuilder(recordedText[0])
            }
        }
    }

}
