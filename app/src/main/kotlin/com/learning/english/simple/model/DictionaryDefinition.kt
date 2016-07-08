package com.learning.english.simple.model

import com.google.gson.annotations.SerializedName

class DictionaryDefinition {
    @SerializedName("word")
    private var word: String? = null
    @SerializedName("partOfSpeech")
    private var partOfSpeech: String? = null
    @SerializedName("text")
    private var text: String? = null
}