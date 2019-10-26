package uk.co.alt236.bluetoothconnectionlog.map.gmaps

import android.content.Context
import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import uk.co.alt236.bluetoothconnectionlog.map.MapColorFactory
import uk.co.alt236.bluetoothconnectionlog.map.model.Poi


internal class MarkerDrawer {

    fun drawMarker(context: Context, map: GoogleMap, poi: Poi) {
        val latLng = LatLng(poi.latitude, poi.longitude)
        val colorFactory = MapColorFactory(poi.graphics)

        map.addCircle(
            CircleOptions()
                .center(latLng)
                .fillColor(colorFactory.circleFillColorWithAlpha)
                .strokeColor(colorFactory.circleEdgeColorWithAlpha)
                .strokeWidth(convertDpToPx(context, 1.0f))
                .radius(poi.accuracy.toDouble())
        )

        val icon = getMarkerIcon(colorFactory.circleFillColor.toHexColor())
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .icon(icon)
                .title(poi.title)
        )
    }

    private fun convertDpToPx(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    // method definition
    private fun getMarkerIcon(color: String): BitmapDescriptor {
        val hsv = FloatArray(3)
        Color.colorToHSV(Color.parseColor(color), hsv)
        return BitmapDescriptorFactory.defaultMarker(hsv[0])
    }

    private fun Int.toHexColor(): String {
        return String.format("#%08X", this)
    }
}