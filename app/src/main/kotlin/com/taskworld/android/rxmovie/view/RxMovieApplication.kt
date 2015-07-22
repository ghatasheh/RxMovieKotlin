package com.taskworld.android.rxmovie.view

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.taskworld.android.domain.R
import fuel.core.Manager
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/13/15.
 */

class RxMovieApplication : Application() {

    var refWatcher: RefWatcher by Delegates.notNull()

    companion object {
        fun refWatcher(context: Context): RefWatcher {
            val app = context.getApplicationContext() as RxMovieApplication
            return app.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        refWatcher = LeakCanary.install(this)

        Manager.sharedInstance.additionalParams = mapOf("api_key" to getString(R.string.movie_db_api_key))
    }

}
 
