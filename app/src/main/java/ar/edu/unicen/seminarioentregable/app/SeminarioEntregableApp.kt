package ar.edu.unicen.seminarioentregable.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SeminarioEntregableApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (isNetworkAvailable()) {

        }else{
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }



}