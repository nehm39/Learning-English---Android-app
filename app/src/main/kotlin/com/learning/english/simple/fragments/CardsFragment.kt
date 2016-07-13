package com.learning.english.simple.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.learning.english.simple.MyApplication

import com.learning.english.simple.R
import com.learning.english.simple.db.Card
import java.security.SecureRandom

class CardsFragment : Fragment() {
    var txtWord : TextView? = null
    var txtDefinition : TextView? = null
    var wordsList : List<Card>? = null
    var random : SecureRandom? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_cards, container, false)
        activity.title = resources.getString(R.string.drawer_menu_cards)
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
        wordsList = MyApplication.getInstance()!!.getDaoSession()!!.cardDao.loadAll()
        getRandomWord()
        return fragmentView
    }

    fun getRandomWord() {
        val randomPosition = random!!.nextInt(wordsList!!.size)
        val currentWord = wordsList!![randomPosition].word
        val currentDefinition = wordsList!![randomPosition].definition
        txtDefinition!!.visibility = View.INVISIBLE
        txtWord!!.text = currentWord
        txtDefinition!!.text = currentDefinition
    }
}
