package uk.co.alt236.bluetoothconnectionlog.map

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Poi(
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    @DrawableRes val drawableId: Int? = null
) : Serializable