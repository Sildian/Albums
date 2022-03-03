package com.sildian.apps.albums.utils

import android.content.Context
import android.net.ConnectivityManager

/*************************************************************************************************
 * Provides with some utils functions
 ************************************************************************************************/

object Utils {

    @JvmStatic
    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
    }
}