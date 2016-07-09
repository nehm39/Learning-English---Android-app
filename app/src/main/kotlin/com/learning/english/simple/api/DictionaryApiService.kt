package com.learning.english.simple.api

import com.learning.english.simple.model.DictionaryAudio
import com.learning.english.simple.model.DictionaryDefinition
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("word.json/{word}/definitions")
    fun getDefinitions(@Path("word") text: String) : Call<List<DictionaryDefinition>>

    @GET("word.json/{word}/audio")
    fun getAudio(@Path("word") text: String) : Call<List<DictionaryAudio>>
}