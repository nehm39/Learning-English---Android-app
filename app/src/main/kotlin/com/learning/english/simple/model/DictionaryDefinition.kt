package com.learning.english.simple.model

import com.google.gson.annotations.SerializedName

class DictionaryDefinition {
    @SerializedName("word")
    var word: String? = null
    @SerializedName("partOfSpeech")
    var partOfSpeech: String? = null
    @SerializedName("text")
    var text: String? = null
}