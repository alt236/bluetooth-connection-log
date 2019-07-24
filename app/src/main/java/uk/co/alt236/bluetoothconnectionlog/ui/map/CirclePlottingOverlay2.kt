package uk.co.alt236.bluetoothconnectionlog.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import org.osmdroid.views.Projection
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem


class CirclePlottingOverlay2<Item : OverlayItem>(
    context: Context,
    list: List<Item>,
    listener: OnItemGestureListener<Item>?
) : ItemizedIconOverlay<Item>(context, list, listener) {
    private val accuracyPaint = Paint()

    init {
        accuracyPaint.strokeWidth = 2.0F
        accuracyPaint.color = Color.BLUE
        accuracyPaint.isAntiAlias = true
    }

    override fun onDrawItem(canvas: Canvas, item: Item, curScreenCoords: Point, pProjection: Projection): Boolean {

        val geoPoint = item.point
        val circleDrawn = if (geoPoint is AccurateGeoPoint && geoPoint.getAccuracyInMeters() > 0) {

            val accuracyRadius = pProjection.metersToEquatorPixels(geoPoint.getAccuracyInMeters())
            /* Draw the inner shadow. */
            accuracyPaint.isAntiAlias = false
            accuracyPaint.alpha = 30
            accuracyPaint.style = Paint.Style.FILL
            canvas.drawCircle(curScreenCoords.x.toFloat(), curScreenCoords.y.toFloat(), accuracyRadius, accuracyPaint)

            /* Draw the edge. */
            accuracyPaint.isAntiAlias = true
            accuracyPaint.alpha = 150
            accuracyPaint.style = Paint.Style.STROKE
            canvas.drawCircle(curScreenCoords.x.toFloat(), curScreenCoords.y.toFloat(), accuracyRadius, accuracyPaint)
            true
        } else {
            false
        }


        return circleDrawn or super.onDrawItem(canvas, item, curScreenCoords, pProjection)
    }

//    override fun onLongPress(e: MotionEvent?, mapView: MapView?): Boolean {
//
//        if (Configuration.getInstance().isDebugMapView) {
//            Log.d(IMapView.LOGTAG, "CirclePlottingOverlay onLongPress")
//        }
//        val pt = mapView!!.projection.fromPixels(e!!.x.toInt(), e.y.toInt(), null) as GeoPoint
//        /*
//             * <b>Note</b></b: when plotting a point off the map, the conversion from
//                 * screen coordinates to map coordinates will return values that are invalid from a latitude,longitude
//                 * perspective. Sometimes this is a wanted behavior and sometimes it isn't. We are leaving it up to you,
//                 * the developer using osmdroid to decide on what is right for your application. See
//                 * <a href="https://github.com/osmdroid/osmdroid/pull/722">https://github.com/osmdroid/osmdroid/pull/722</a>
//                 * for more information and the discussion associated with this.
//             */
//
//        //just in case the point is off the map, let's fix the coordinates
//        if (pt.longitude < -180)
//            pt.longitude = pt.longitude + 360
//        if (pt.longitude > 180)
//            pt.longitude = pt.longitude - 360
//        //latitude is a bit harder. see https://en.wikipedia.org/wiki/Mercator_projection
//        if (pt.latitude > 85.05112877980659)
//            pt.latitude = 85.05112877980659
//        if (pt.latitude < -85.05112877980659)
//            pt.latitude = -85.05112877980659
//
//        val circle = Polygon.pointsAsCircle(pt, distanceKm.toDouble())
//        val p = Polygon(mapView)
//        p.points = circle
//        p.title = "A circle"
//        mapView.overlayManager.add(p)
//        mapView.invalidate()
//        return true
//
//    }
}
