package com.example.weatherapp.data.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import androidx.core.content.ContextCompat
import com.example.weatherapp.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {

    override suspend fun getCurrentLocation(): Pair<Double, Double>? {
        val hasAccessFineLocationPermission = ContextCompat
            .checkSelfPermission(application, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED

        val hasCoarseFineLocationPermission = ContextCompat
            .checkSelfPermission(application, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGpsEnabled =
            locationManager.isProviderEnabled(NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                GPS_PROVIDER
            )

        return if (!hasAccessFineLocationPermission && !hasCoarseFineLocationPermission && !isGpsEnabled) {
            null
        } else {
            suspendCancellableCoroutine { cont ->
                locationClient.lastLocation.apply {
                    if (isComplete) {
                        if (isSuccessful) {
                            cont.resume(Pair(result.latitude, result.longitude))
                        } else {
                            cont.resume(null)
                        }
                        return@suspendCancellableCoroutine
                    }
                    addOnSuccessListener { location ->
                        if (location != null) {
                            cont.resume(Pair(location.latitude, location.longitude))
                        }
                    }
                    addOnFailureListener {
                        cont.resume(null)
                    }
                    addOnCanceledListener {
                        cont.cancel()
                    }
                }
            }
        }
    }
}
