package com.learning.english.simple

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.learning.english.simple.fragments.*
import com.learning.english.simple.utils.Utils

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var doubleBackToExitPressedOnce = false

    //TODO: start screen
    //TODO: informacje
    //TODO: splash screen
    //TODO: hide keyboard on drawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        val toggle = object : ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            }
        }
        drawer!!.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        navigationView!!.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, StartFragment())
                    .commit()

            navigationView.getMenu().findItem(R.id.drawer_menu_start).setChecked(true);
        }

        val headerLayout = navigationView.getHeaderView(0)
        val headerAppName = headerLayout.findViewById(R.id.nav_header_app_name) as TextView
        val typeface = Typeface.createFromAsset(assets, "Mightype.otf")
        headerAppName.setTypeface(typeface)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            Utils.showToast(this, resources.getString(R.string.press_again_to_exit))
            doubleBackToExitPressedOnce = true
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragmentManager = supportFragmentManager
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.drawer_menu_exercises -> {
                fragment = ExercisesFragment()
            }
            R.id.drawer_menu_cards -> {
                fragment = CardsFragment()
            }
            R.id.drawer_menu_dictionary -> {
                fragment = DictionaryFragment()
            }
            R.id.drawer_menu_translate -> {
                fragment = TranslateFragment()
            }
            R.id.drawer_menu_lessons -> {
                fragment = LessonsListFragment()
                val bundle = Bundle()
                bundle.putInt(LessonsListFragment.LESSON_TYPE_KEY, Utils.LESSON_TYPE_TEXT)
                fragment.arguments = bundle
            }
            R.id.drawer_menu_video -> {
                fragment = LessonsListFragment()
                val bundle = Bundle()
                bundle.putInt(LessonsListFragment.LESSON_TYPE_KEY, Utils.LESSON_TYPE_VIDEO)
                fragment.arguments = bundle
            }
        }

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }
}
