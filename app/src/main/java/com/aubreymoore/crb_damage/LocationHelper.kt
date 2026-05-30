package com.aubreymoore.crb_damage

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationHelper(context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    var lastLocation: Location? = null

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY, null
        ).addOnSuccessListener { location ->
            lastLocation = location
        }

        // Also request ongoing updates
        val request = com.google.android.gms.location.LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 5000L
        ).build()

        fusedLocationClient.requestLocationUpdates(
            request,
            object : com.google.android.gms.location.LocationCallback() {
                override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                    lastLocation = result.lastLocation
                }
            },
            android.os.Looper.getMainLooper()
        )
    }

    fun getLatitude() = lastLocation?.latitude
    fun getLongitude() = lastLocation?.longitude
}