package uk.co.alt236.bluetoothconnectionlog.map.osm

import androidx.core.content.ContextCompat
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MinimapOverlay
import org.osmdroid.views.overlay.OverlayItem
import uk.co.alt236.bluetoothconnectionlog.map.MapColorFactory
import uk.co.alt236.bluetoothconnectionlog.map.model.Poi
import uk.co.alt236.bluetoothconnectionlog.map.osm.overlays.AccurateGeoPoint
import uk.co.alt236.bluetoothconnectionlog.map.osm.overlays.CirclePlottingOverlay2

internal class MapWrapper(private val map: MapView) {
    private val context = map.context.applicationContext

    init {
        val tileProvider = MapTileProviderBasic(context, TileSourceFactory.DEFAULT_TILE_SOURCE)
        tileProvider.setOfflineFirst(false)

        map.tileProvider = tileProvider
        map.tileProvider.tileCache.protectedTileComputers.clear()
        map.tileProvider.tileCache.setAutoEnsureCapacity(false)

        map.setMultiTouchControls(true)

        val minimapOverlay = MinimapOverlay(context, map.tileRequestCompleteHandler)
        minimapOverlay.zoomDifference = 5
        map.overlays.add(minimapOverlay)
    }


    fun onResume() {
        map.onResume()
    }

    fun onPause() {
        map.onPause()
    }

    fun onDetach() {
        map.onDetach()
    }

    fun centerOn(poi: Poi) {
        val colorFactory = MapColorFactory(poi.graphics)
        val items = ArrayList<OverlayItem>()
        val overlayItem = createOverlayItem(poi)

        items.add(overlayItem)

        val overlay =
            CirclePlottingOverlay2(
                map.context,
                colorFactory,
                items,
                null
            )

        map.overlays.add(overlay)
        map.controller.zoomTo(TARGET_ZOOM_LEVEL)
        map.controller.animateTo(GeoPoint(overlayItem.point))
    }

    private fun createOverlayItem(poi: Poi): OverlayItem {
        val geoPoint = AccurateGeoPoint(
            aLatitude = poi.latitude,
            aLongitude = poi.longitude,
            aAccuracyInMeters = poi.accuracy
        )

        val item = OverlayItem(poi.title, "", geoPoint)

        if (poi.graphics.markerDrawableId != null) {
            val drawable = ContextCompat.getDrawable(map.context, poi.graphics.markerDrawableId)!!
            item.setMarker(drawable)
        }

        return item
    }

    private companion object {
        private const val TARGET_ZOOM_LEVEL = 17.0
    }
}