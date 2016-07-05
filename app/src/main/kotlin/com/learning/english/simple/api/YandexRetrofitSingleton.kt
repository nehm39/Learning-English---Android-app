package com.learning.english.simple.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YandexRetrofitSingleton {

    private val API_URL = "https://translate.yandex.net/api/v1.5/tr.json/"
    private val API_KEY = "trnsl.1.1.20160705T062522Z.75931fe53d38511f.ff23ad418c230c0cd876d44fc0c2334e5cee1173"
    private var apiService: YandexApiService? = null
    private var retrofit: Retrofit? = null

    val client: YandexApiService
        get() {
            if (apiService == null) {
                apiService = getRetrofit().create(YandexApiService::class.java)
            }
            return apiService!!
        }

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            val gson = Gson()

            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.addInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("key", API_KEY).build()
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