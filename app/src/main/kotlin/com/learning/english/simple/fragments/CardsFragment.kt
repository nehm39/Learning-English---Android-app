package com.learning.english.simple.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView

import com.learning.english.simple.R
import java.security.SecureRandom

class CardsFragment : Fragment() {
    var txtWord : TextView? = null
    var txtDefinition : TextView? = null
    var wordsList : List<String>? = null
    var random : SecureRandom? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_cards, container, false)
        random = SecureRandom()
        txtWord = fragmentView.findViewById(R.id.cards_word) as TextView
        txtDefinition = fragmentView.findViewById(R.id.cards_definition) as TextView
        val layout = fragmentView.findViewById(R.id.cards_layout) as RelativeLayout
        layout.setOnClickListener {
            if (txtDefinition!!.visibility == View.INVISIBLE) {
                txtDefinition!!.visibility = View.VISIBLE
            } else {
                getRandomWord()
            }
        }
        wordsList = resources.getStringArray(R.array.cards_names).asList()
        getRandomWord()
        return fragmentView
    }

    fun getRandomWord() {
        val randomPosition = random!!.nextInt(wordsList!!.size)
        val currentWord = resources.getStringArray(R.array.cards_names)[randomPosition]
        val currentDefinition = resources.getStringArray(R.array.cards_names_values)[randomPosition]
        txtDefinition!!.visibility = View.INVISIBLE
        txtWord!!.text = currentWord
        txtDefinition!!.text = currentDefinition
    }
}
