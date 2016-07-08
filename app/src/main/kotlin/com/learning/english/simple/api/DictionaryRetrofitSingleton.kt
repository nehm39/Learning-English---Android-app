package com.learning.english.simple.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DictionaryRetrofitSingleton {

    private val API_URL = "http://api.wordnik.com:80/v4/"
    private val API_KEY = "a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5"
    private var apiService: DictionaryApiService? = null
    private var retrofit: Retrofit? = null

    val client: DictionaryApiService
        get() {
            if (apiService == null) {
                apiService = getRetrofit().create(DictionaryApiService::class.java)
            }
            return apiService!!
        }

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            val gson = Gson()

            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.addInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("api_key", API_KEY).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(interceptor)

            retrofit = Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClientBuilder.build()).build()
        }
        return retrofit!!
    }
}