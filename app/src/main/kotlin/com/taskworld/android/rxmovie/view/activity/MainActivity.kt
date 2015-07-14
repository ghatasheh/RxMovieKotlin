package com.taskworld.android.rxmovie.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.taskworld.android.rxmovie.R
import com.taskworld.android.domain.network.TheMovieDB
import com.taskworld.android.rxmovie.view.fragment.NavigationDrawerFragment
import kotlinx.android.synthetic.activity_main.mainDrawerLayout
import kotlinx.android.synthetic.activity_main.mainNavigationDrawer
import rx.Observable
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

            // New Fragment to Attach
            val fragment = PlaceholderFragment.newInstance(position + 1)
            fragment.onFragmentAttached = { number ->
               when(number) {
                   1 -> titleText = getString(R.string.title_section1)
                   2 -> titleText = getString(R.string.title_section2)
                   3 -> titleText = getString(R.string.title_section3)
               }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        var onFragmentAttached: ((Int) -> Unit)? = null

        companion object {

            private val ARG_SECTION_NUMBER = "section_number"

            public fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.setArguments(args)
                return fragment
            }
        }

        override fun onCreateView(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
            val rootView = inflater!!.inflate(R.layout.fragment_main, container, false)
            return rootView
        }

        override fun onAttach(activity: Activity?) {
            super.onAttach(activity)
            onFragmentAttached?.invoke(getArguments().getInt(ARG_SECTION_NUMBER))
        }

        override fun onDetach() {
            super.onDetach()
            onFragmentAttached = null
        }

    }

}
