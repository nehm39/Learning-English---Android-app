package com.learning.english.simple.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.english.simple.R
import com.learning.english.simple.api.DictionaryRetrofitSingleton
import com.learning.english.simple.model.DictionaryDefinition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DictionaryFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val dictionaryService = DictionaryRetrofitSingleton.client
        val call = dictionaryService.getDefinitions("die")
        call.enqueue(object : Callback<List<DictionaryDefinition>> {
            override fun onResponse(call: Call<List<DictionaryDefinition>>?, response: Response<List<DictionaryDefinition>>?) {
                var body = response?.body()
                var x = 122
                x = 4
            }

            override fun onFailure(call: Call<List<DictionaryDefinition>>?, t: Throwable?) {
            }

        })

        return inflater!!.inflate(R.layout.fragment_dictionary, container, false)
    }

}
