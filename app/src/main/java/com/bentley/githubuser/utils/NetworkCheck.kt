package com.bentley.githubuser.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import javax.inject.Inject

class NetworkCheck @Inject constructor(context: Context) {

    private var isConnected: Boolean? = false

    private val connMgr: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val builder = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    private val mNetworkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            if (connMgr.activeNetwork != null) {
                isConnected = true
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            if (connMgr.activeNetwork == null) {
                isConnected = false
            }
        }
    }

    fun registerNetworkCallback() {
        connMgr.registerNetworkCallback(builder, mNetworkCallback)
    }

    fun unRegisterNetworkCallback() {
        connMgr.unregisterNetworkCallback(mNetworkCallback)
    }

    fun isConnected() = isConnected!!
}