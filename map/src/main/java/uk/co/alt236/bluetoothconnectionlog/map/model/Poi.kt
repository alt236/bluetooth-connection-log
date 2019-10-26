package uk.co.alt236.bluetoothconnectionlog.map.model

import java.io.Serializable

data class Poi(
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val graphics: Graphics
) : Serializable