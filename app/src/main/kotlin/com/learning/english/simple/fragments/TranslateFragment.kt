package com.learning.english.simple.fragments


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.text.method.ScrollingMovementMethod
import android.view.*
import android.widget.AbsListView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.avast.android.dialogs.fragment.ListDialogFragment
import com.avast.android.dialogs.fragment.ProgressDialogFragment
import com.avast.android.dialogs.iface.IListDialogListener
import com.learning.english.simple.R
import com.learning.english.simple.activity.MainActivity
import com.learning.english.simple.api.YandexRetrofitSingleton
import com.learning.english.simple.model.YandexTranslation
import com.learning.english.simple.utils.SharedPreferencesUtils
import com.learning.english.simple.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TranslateFragment : Fragment(), IListDialogListener {

    val TRANSLATION_OPTIONS_DIALOG = 1234
    val TRANSLATION_PROGRESS_DIALOG = 1235
    val VOICE_REQUEST_OK = 7834

    var fragmentView: View? = null
    var etxtTextToTranslate: EditText? = null
    var inputLayoutTextToTranslate: TextInputLayout? = null
    var etxtOutput: TextView? = null
    var btnTranslate: Button? = null
    var mainActivity: MainActivity? = null

    var translationOptionValue = "en-pl"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater!!.inflate(R.layout.fragment_translate, container, false)
        activity.title = resources.getString(R.string.drawer_menu_translate)
        etxtTextToTranslate = fragmentView!!.findViewById(R.id.etxt_text_to_translate) as EditText
        inputLayoutTextToTranslate = fragmentView!!.findViewById(R.id.input_layout_text_to_translate) as TextInputLayout
        etxtOutput = fragmentView!!.findViewById(R.id.etxt_translated_text) as TextView
        etxtOutput!!.setMovementMethod(ScrollingMovementMethod());
        btnTranslate = fragmentView!!.findViewById(R.id.btn_translate) as Button
        translationOptionValue = SharedPreferencesUtils.getLanguageTranslationOption(activity)
        mainActivity = activity as MainActivity

        btnTranslate!!.setOnClickListener {
            if (validateTextInput() && Utils.isInternetConnectionAvailable(activity)) {
                val progressDialog = ProgressDialogFragment.createBuilder(activity, activity.supportFragmentManager)
                        .setMessage(resources.getString(R.string.translation_in_progress))
                        .setRequestCode(TRANSLATION_PROGRESS_DIALOG)
                        .show();
                val yandexService = YandexRetrofitSingleton.client
                val call = yandexService.getTranslation(etxtTextToTranslate!!.text.toString(), translationOptionValue)
                call.enqueue(object : Callback<YandexTranslation> {
                    override fun onFailure(call: Call<YandexTranslation>?, t: Throwable?) {
                        progressDialog.dismiss()
                    }

                    override fun onResponse(call: Call<YandexTranslation>?, response: Response<YandexTranslation>?) {
                        var finalString = ""
                        response!!.body().getText().forEach {
                            finalString += it;
                        }
                        etxtOutput!!.text = SpannableStringBuilder(finalString)
                        progressDialog.dismiss()
                    }
                })
            }
        }

        return fragmentView
    }

    fun validateTextInput(): Boolean {
        if (etxtTextToTranslate!!.text.trim().isEmpty()) {
            inputLayoutTextToTranslate!!.error = resources.getString(R.string.empty_input_field)
            return false
        } else {
            inputLayoutTextToTranslate!!.isErrorEnabled = false;
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.translate_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.translate_change_language -> {
                ListDialogFragment
                        .createBuilder(activity, mainActivity!!.supportFragmentManager)
                        .setTitle(resources.getString(R.string.translation_choose_language))
                        .setItems(resources.getStringArray(R.array.translations_options))
                        .setRequestCode(TRANSLATION_OPTIONS_DIALOG)
                        .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
                        .setSelectedItem(resources.getStringArray(R.array.translations_options_values).indexOf(translationOptionValue))
                        .setCancelable(false)
                        .setTargetFragment(this, TRANSLATION_OPTIONS_DIALOG)
                        .show()
            }
            R.id.translate_voice_input -> {
                if (translationOptionValue.equals("en-pl")) {
                    Utils.startVoiceInput(activity, VOICE_REQUEST_OK, "en-US")
                } else {
                    Utils.startVoiceInput(activity, VOICE_REQUEST_OK, "pl-PL")
                }
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val voiceInput = Utils.getVoiceInput(requestCode, resultCode, data, VOICE_REQUEST_OK)
        if (voiceInput != null) {
            etxtTextToTranslate!!.text = SpannableStringBuilder(voiceInput)
        }
    }

    override fun onListItemSelected(value: CharSequence?, number: Int, requestCode: Int) {
        when (requestCode) {
            TRANSLATION_OPTIONS_DIALOG -> {
                translationOptionValue = resources.getStringArray(R.array.translations_options_values)[number]
                SharedPreferencesUtils.saveLanguageTranslationOption(activity, translationOptionValue)
            }
        }
    }

}