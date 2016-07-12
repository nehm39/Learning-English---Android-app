package com.learning.english.simple.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SharedPreferencesUtils {
    private val LANGUAGE_TRANSLATION_OPTION_KEY = "LANGUAGE_TRANSLATION_OPTION_KEY"
    private val LAST_OPENED_FRAGMENT = "LAST_OPENED_FRAGMENT"

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

    fun getLastOpenedFragment(context: Context) : Int {
        return getDefaultSharedPreferences(context)
                .getInt(LAST_OPENED_FRAGMENT, 0)
    }

    fun saveLastOpenedFragment(context: Context, value: Int) {
        getDefaultSharedPreferences(context)
                .edit()
                .putInt(LAST_OPENED_FRAGMENT, value)
                .commit()
    }
}