package uk.co.alt236.bluetoothconnectionlog.map


import androidx.core.graphics.ColorUtils
import uk.co.alt236.bluetoothconnectionlog.map.model.Graphics

internal class MapColorFactory(graphics: Graphics) {
    private val color = graphics.accuracyCircleColor

    val circleFillColorAlpha = 30
    val circleFillColor = color
    val circleFillColorWithAlpha =
        ColorUtils.setAlphaComponent(circleFillColor, circleFillColorAlpha)

    val circleEdgeColorAlpha = 150
    val circleEdgeColor = color
    val circleEdgeColorWithAlpha =
        ColorUtils.setAlphaComponent(circleEdgeColor, circleEdgeColorAlpha)
}