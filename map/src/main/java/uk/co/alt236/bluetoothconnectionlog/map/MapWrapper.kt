package uk.co.alt236.bluetoothconnectionlog.map

import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MinimapOverlay
import org.osmdroid.views.overlay.OverlayItem
import uk.co.alt236.bluetoothconnectionlog.map.overlays.AccurateGeoPoint
import uk.co.alt236.bluetoothconnectionlog.map.overlays.CirclePlottingOverlay2

internal class MapWrapper(private val map: MapView) {

    init {
        val context = map.context

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

    fun centerOn(poi: Poi) {
        val geoPoint = AccurateGeoPoint(
            aLatitude = poi.latitude,
            aLongitude = poi.longitude,
            aAccuracyInMeters = poi.accuracy
        )

        val items = ArrayList<OverlayItem>()
        items.add(OverlayItem("Title", "Description", geoPoint))

        val overlay =
            CirclePlottingOverlay2(
                map.context,
                items,
                null
            )

        map.overlays.add(overlay)
        map.controller.zoomTo(TARGET_ZOOM_LEVEL)
        map.controller.animateTo(GeoPoint(geoPoint.latitude, geoPoint.longitude))
    }

    private companion object {
        private const val TARGET_ZOOM_LEVEL = 17.0
    }
}