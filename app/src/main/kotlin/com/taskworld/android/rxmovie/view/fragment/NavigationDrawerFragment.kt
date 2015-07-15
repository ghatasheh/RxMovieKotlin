package com.taskworld.android.rxmovie.view.fragment

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.ArrayAdapter
import com.taskworld.android.rxmovie.R
import kotlinx.android.synthetic.fragment_navigation_drawer.view.navigationDrawerListView
import kotlin.properties.Delegates

public class NavigationDrawerFragment : Fragment() {

    private val STATE_SELECTED_POSITION = "selected_navigation_drawer_position"

    //widgets
    var navigationDrawerLayout: DrawerLayout by Delegates.notNull()
    var navigationContainerFragment: Fragment by Delegates.notNull()

    //adapter
    var drawerToggle: ActionBarDrawerToggle? = null

    //data
    var onDrawerItemSelected: ((Int) -> Unit)? = null

    private var mCurrentSelectedPosition = 0
    private var mFromSavedInstanceState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION)
            mFromSavedInstanceState = true
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true)

        getView().navigationDrawerListView.setAdapter(
                ArrayAdapter<String>(getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                listOf(getString(R.string.title_section1), getString(R.string.title_section2), getString(R.string.title_section3))))

        getView().navigationDrawerListView.setOnItemClickListener { adapterView, view, position, id ->
            selectItem(position)
        }

        getView().navigationDrawerListView.setItemChecked(mCurrentSelectedPosition, true)

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_navigation_drawer, container, false)

    fun isDrawerOpen() = navigationDrawerLayout.isDrawerOpen(navigationContainerFragment.getView())

    fun setUp(fragment: Fragment, drawerLayout: DrawerLayout) {
        navigationContainerFragment = fragment
        navigationDrawerLayout = drawerLayout

        // set a custom shadow that overlays the main content when the drawer opens
        navigationDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, android.support.v4.view.GravityCompat.START)
        // set up the drawer's list reactiveandroid.view with items and click listener

        val actionBar = getActionBar()
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        drawerToggle = object : ActionBarDrawerToggle(getActivity(), /* host Activity */
                navigationDrawerLayout, /* DrawerLayout object */
                R.string.navigation_drawer_open, /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */) {
            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                if (!isAdded()) {
                    return
                }

                getActivity().supportInvalidateOptionsMenu() // calls onPrepareOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                if (!isAdded()) {
                    return
                }

                getActivity().supportInvalidateOptionsMenu() // calls onPrepareOptionsMenu()
            }
        }

        // Defer code dependent on restoration of previous instance state.
        navigationDrawerLayout.post { drawerToggle!!.syncState() }

        navigationDrawerLayout.setDrawerListener(drawerToggle)
    }

    fun selectItem(position: Int) {
        mCurrentSelectedPosition = position

        getView().navigationDrawerListView.setItemChecked(position, true)

        //close
        navigationDrawerLayout.closeDrawer(navigationContainerFragment.getView())

        onDrawerItemSelected?.invoke(position)
    }

    override fun onDetach() {
        super.onDetach()
        onDrawerItemSelected = null
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition)
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration?) {
        super.onConfigurationChanged(newConfig)
        // Forward the new configuration the drawer toggle component.
        drawerToggle!!.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?, inflater: android.view.MenuInflater?) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (isDrawerOpen()) {
            inflater!!.inflate(R.menu.global, menu)
            showGlobalContextActionBar()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem?): Boolean {
        if (drawerToggle!!.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private fun showGlobalContextActionBar() {
        val actionBar = getActionBar()
        actionBar.setDisplayShowTitleEnabled(true)
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD)
        actionBar.setTitle(R.string.app_name)
    }

    private fun getActionBar(): ActionBar {
        return (getActivity() as AppCompatActivity).getSupportActionBar()
    }

}
