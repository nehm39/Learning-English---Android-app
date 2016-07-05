package com.learning.english.simple.model

import com.google.gson.annotations.SerializedName
import java.util.*

class YandexTranslation {

    @SerializedName("code")
    private var code: Int = 0
    @SerializedName("lang")
    private var lang: String? = null
    @SerializedName("text")
    private var text: List<String> = ArrayList<String>()

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int) {
        this.code = code
    }


    fun getLang(): String? {
        return lang
    }

    fun setLang(lang: String) {
        this.lang = lang
    }

    fun getText(): List<String> {
        return text
    }

    fun setText(text: List<String>) {
        this.text = text
    }
}