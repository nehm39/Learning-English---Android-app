package com.learning.english.simple

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.learning.english.simple.fragments.LessonsListFragment
import com.learning.english.simple.fragments.StartFragment
import com.learning.english.simple.fragments.TranslateFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        navigationView!!.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, StartFragment())
                    .commit()

            navigationView.getMenu().findItem(R.id.drawer_menu_start).setChecked(true);
        }

        Utils.showToast(this, "teeeeeeeeeeeeeeeeeest")
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragmentManager = fragmentManager
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.drawer_menu_translate -> {
                fragment = TranslateFragment()
            }
            R.id.drawer_menu_video -> {
                fragment = LessonsListFragment()
                val bundle = Bundle()
                bundle.putInt(LessonsListFragment.LESSON_TYPE_KEY, Utils.LESSON_TYPE_TEXT)
                fragment.arguments = bundle
            }
            R.id.drawer_menu_lessons -> {
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
