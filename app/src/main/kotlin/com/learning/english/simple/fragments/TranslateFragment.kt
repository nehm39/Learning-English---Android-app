package com.learning.english.simple.fragments


import android.app.Fragment
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.*
import android.widget.Button
import android.widget.EditText
import com.learning.english.simple.R
import com.learning.english.simple.api.YandexRetrofitSingleton
import com.learning.english.simple.model.YandexTranslation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TranslateFragment : Fragment() {

    var fragmentView : View? = null
    var etxtTextToTranslate : EditText? = null
    var etxtOutput : EditText? = null
    var btnTranslate : Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater!!.inflate(R.layout.fragment_translate, container, false)
        etxtTextToTranslate = fragmentView!!.findViewById(R.id.etxt_text_to_translate) as EditText
        etxtOutput = fragmentView!!.findViewById(R.id.etxt_translated_text) as EditText
        btnTranslate = fragmentView!!.findViewById(R.id.btn_translate) as Button

        btnTranslate!!.setOnClickListener {
            val yandexService = YandexRetrofitSingleton.client
            val call = yandexService.getTranslation(etxtTextToTranslate!!.text.toString(), "en-pl")
            call.enqueue(object : Callback<YandexTranslation> {
                override fun onFailure(call: Call<YandexTranslation>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<YandexTranslation>?, response: Response<YandexTranslation>?) {
                    var finalString = ""
                    response!!.body().getText().forEach {
                        finalString += it;
                    }
                    etxtOutput!!.text = SpannableStringBuilder(finalString)
                }
            })
        }

        return fragmentView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.translate_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }

}