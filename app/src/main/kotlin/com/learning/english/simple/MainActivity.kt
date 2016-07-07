package com.learning.english.simple

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.learning.english.simple.api.YandexRetrofitSingleton
import com.learning.english.simple.fragments.LessonsListFragment
import com.learning.english.simple.fragments.StartFragment
import com.learning.english.simple.fragments.TranslateFragment
import com.learning.english.simple.model.YandexTranslation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        val fragmentManager = fragmentManager
        var fragment = null as Fragment?

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            fragment = TranslateFragment()
        } else if (id == R.id.nav_share) {
            fragment = LessonsListFragment()
            val bundle = Bundle()
            bundle.putInt(LessonsListFragment.LESSON_TYPE_KEY, Utils.LESSON_TYPE_TEXT)
            fragment.arguments = bundle
        } else if (id == R.id.nav_send) {
            fragment = LessonsListFragment()
            val bundle = Bundle()
            bundle.putInt(LessonsListFragment.LESSON_TYPE_KEY, Utils.LESSON_TYPE_VIDEO)
            fragment.arguments = bundle
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
