package com.weater_app.weater_app.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import kotlin.coroutines.resume

class Location_Manager(
    private val context : Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Location? {
        val hasGrantedFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasGrantedCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled || !(hasGrantedCoarseLocationPermission || hasGrantedFineLocationPermission)) {
            return null
        }

        // Prova lastLocation
        val lastLocation = fusedLocationProviderClient.lastLocation.await()
        if (lastLocation != null) return lastLocation

        // Se lastLocation è null, richiedi aggiornamenti attivi
        return suspendCancellableCoroutine { cont ->
            val request = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                1000 // ogni 1 secondo
            ).setMaxUpdates(1) // solo una posizione e poi stop
                .build()

            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    fusedLocationProviderClient.removeLocationUpdates(this)
                    cont.resume(result.lastLocation)
                }

                override fun onLocationAvailability(availability: LocationAvailability) {
                    if (!availability.isLocationAvailable) {
                        // Se non disponibile, fai timeout dopo un po'
                    }
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(
                request,
                callback,
                Looper.getMainLooper()
            )

            cont.invokeOnCancellation {
                fusedLocationProviderClient.removeLocationUpdates(callback)
            }
        }
    }


}