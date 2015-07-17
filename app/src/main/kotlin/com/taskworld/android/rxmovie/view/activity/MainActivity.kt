package com.taskworld.android.rxmovie.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.taskworld.android.rxmovie.R
import com.taskworld.android.rxmovie.view.fragment.ItemListFragment
import com.taskworld.android.rxmovie.view.fragment.NavigationDrawerFragment
import fuel.util.build
import kotlinx.android.synthetic.activity_main.mainDrawerLayout
import kotlinx.android.synthetic.activity_main.mainNavigationDrawer
import kotlin.properties.Delegates

public class MainActivity : AppCompatActivity() {

    //widgets
    val mainNavigationDrawerFragment: NavigationDrawerFragment by Delegates.lazy { mainNavigationDrawer as NavigationDrawerFragment }

    //data
    var titleText: CharSequence by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        titleText = getTitle()

        setUpDrawerFragment()
    }

    override fun onStart() {
        super.onStart()
    }

    fun setUpDrawerFragment() {
        // Set up the drawer.
        mainNavigationDrawerFragment.setUp(mainNavigationDrawer, mainDrawerLayout as DrawerLayout)

        // Set up hooks
        mainNavigationDrawerFragment.onDrawerItemSelected = { position ->

            val fragment = ItemListFragment.newInstance(position)
            build(fragment) {
                val titleRes = when(position) {
                    0 -> R.string.title_section1
                    1 -> R.string.title_section2
                    2 -> R.string.title_section3
                    else -> throw IllegalStateException("Error unknown number of section")
                }
                onFragmentAttached = { titleText = getString(titleRes) }
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit()
        }
    }

    public fun restoreActionBar() {
        val actionBar = getSupportActionBar()
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD)
        actionBar.setDisplayShowTitleEnabled(true)
        actionBar.setTitle(titleText)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        if (!mainNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main_activity, menu)
            restoreActionBar()
            return true
        }
        return super<AppCompatActivity>.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item!!.getItemId()

        if (id == R.id.action_settings) {
            val i = Intent(this, javaClass<SignInActivity>())
            startActivity(i)
            return true
        }

        return super<AppCompatActivity>.onOptionsItemSelected(item)
    }

}
