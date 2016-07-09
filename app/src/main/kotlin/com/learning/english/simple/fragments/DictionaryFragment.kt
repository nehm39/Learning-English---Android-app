package com.learning.english.simple.fragments


import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.learning.english.simple.R
import com.learning.english.simple.api.DictionaryRetrofitSingleton
import com.learning.english.simple.model.DictionaryAudio
import com.learning.english.simple.model.DictionaryDefinition
import com.learning.english.simple.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DictionaryFragment : Fragment() {
    var txtWordName: TextView? = null
    var txtWordDefinition: TextView? = null
    var btnSearch: ImageButton? = null
    var etxtSearchedWord: EditText? = null

    var resultLayout: RelativeLayout? = null
    var emptyLayout: RelativeLayout? = null
    var progressBar: ProgressBar? = null

    var definitionResponse: Response<List<DictionaryDefinition>>? = null
    var audioResponse: Response<List<DictionaryAudio>>? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_dictionary, container, false)
        txtWordName = fragmentView.findViewById(R.id.dictionary_word_text) as TextView
        txtWordDefinition = fragmentView.findViewById(R.id.dictionary_definitions_text) as TextView
        btnSearch = fragmentView.findViewById(R.id.dictionary_img_btn) as ImageButton
        etxtSearchedWord = fragmentView.findViewById(R.id.etxt_searched_word) as EditText
        resultLayout = fragmentView.findViewById(R.id.dictionary_result_layout) as RelativeLayout
        emptyLayout = fragmentView.findViewById(R.id.dictionary_empty_layout) as RelativeLayout
        progressBar = fragmentView.findViewById(R.id.dictionary_progress_bar) as ProgressBar

        btnSearch!!.setOnClickListener {
            btnSearch!!.isEnabled = false
            progressBar!!.visibility = View.VISIBLE
            emptyLayout!!.visibility = View.GONE
            resultLayout!!.visibility = View.GONE
            audioResponse = null
            definitionResponse = null

            val dictionaryService = DictionaryRetrofitSingleton.client
            val call = dictionaryService.getDefinitions(etxtSearchedWord!!.text.toString())
            call.enqueue(object : Callback<List<DictionaryDefinition>> {
                override fun onResponse(call: Call<List<DictionaryDefinition>>?, response: Response<List<DictionaryDefinition>>?) {
                    definitionResponse = response
                    validateResults()
                }

                override fun onFailure(call: Call<List<DictionaryDefinition>>?, t: Throwable?) {
                    Utils.showToast(activity, resources.getString(R.string.error_try_again))
                }

            })

            val callForAudio = dictionaryService.getAudio(etxtSearchedWord!!.text.toString())
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
                txtWordName!!.text = etxtSearchedWord!!.text
                txtWordDefinition!!.text = definition

                val mPlayer = MediaPlayer()
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                try {
                    mPlayer.setDataSource(audioBody[0].fileUrl)
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}
