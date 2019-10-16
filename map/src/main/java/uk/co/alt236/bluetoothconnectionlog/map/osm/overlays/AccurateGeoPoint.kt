package uk.co.alt236.bluetoothconnectionlog.map.osm.overlays

import org.osmdroid.api.IGeoPoint

internal data class AccurateGeoPoint(
    private val aLatitude: Double = 0.0,
    private val aLongitude: Double = 0.0,
    private val aAltitude: Double = 0.0,
    private val aAccuracyInMeters: Float = 0.0F
) : IGeoPoint {

    private val aLongitudeE6 by lazy {
        (longitude * 1E6).toInt()
    }

    private val aLatitudeE6 by lazy {
        (latitude * 1E6).toInt()
    }

    override fun getLatitude(): Double = aLatitude
    override fun getLongitude(): Double = aLongitude
    override fun getLatitudeE6(): Int = aLatitudeE6
    override fun getLongitudeE6(): Int = aLongitudeE6

    fun getAccuracyInMeters() = aAccuracyInMeters
}