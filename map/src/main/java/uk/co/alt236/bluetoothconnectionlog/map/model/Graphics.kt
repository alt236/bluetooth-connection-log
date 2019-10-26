package uk.co.alt236.bluetoothconnectionlog.map.model

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import java.io.Serializable

data class Graphics(
    @ColorInt val accuracyCircleColor: Int,
    @DrawableRes val markerDrawableId: Int? = null
) : Serializable