package com.learning.english.simple.api

import com.learning.english.simple.model.YandexTranslation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YandexApiService {
    @GET("translate")
    fun getTranslation(@Query("text") text: String, @Query("lang") language: String): Call<YandexTranslation>
}