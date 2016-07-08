package com.learning.english.simple.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SharedPreferencesUtils {
    private val LANGUAGE_TRANSLATION_OPTION_KEY = "LANGUAGE_TRANSLATION_OPTION_KEY"

    fun getDefaultSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun getLanguageTranslationOption(context: Context) : String {
        return getDefaultSharedPreferences(context)
        .getString(LANGUAGE_TRANSLATION_OPTION_KEY, "en-pl")
    }

    fun saveLanguageTranslationOption(context: Context, value: String) {
        getDefaultSharedPreferences(context)
        .edit()
        .putString(LANGUAGE_TRANSLATION_OPTION_KEY, value)
        .commit()
    }
}