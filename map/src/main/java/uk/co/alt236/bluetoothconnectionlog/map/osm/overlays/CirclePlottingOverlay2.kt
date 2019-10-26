package uk.co.alt236.bluetoothconnectionlog.map.osm.overlays

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import org.osmdroid.views.Projection
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
import uk.co.alt236.bluetoothconnectionlog.map.MapColorFactory

internal class CirclePlottingOverlay2<Item : OverlayItem>(
    context: Context,
    private val colorFactory: MapColorFactory,
    list: List<Item>,
    listener: OnItemGestureListener<Item>?
) : ItemizedIconOverlay<Item>(context, list, listener) {
    private val accuracyPaint = Paint()

    init {
        accuracyPaint.strokeWidth = 2.0F
        accuracyPaint.isAntiAlias = true
    }

    override fun onDrawItem(
        canvas: Canvas,
        item: Item,
        curScreenCoords: Point,
        pProjection: Projection
    ): Boolean {

        val geoPoint = item.point
        val circleDrawn = if (geoPoint is AccurateGeoPoint && geoPoint.getAccuracyInMeters() > 0) {

            val accuracyRadius = pProjection.metersToEquatorPixels(geoPoint.getAccuracyInMeters())
            /* Draw the inner shadow. */
            accuracyPaint.color = colorFactory.circleFillColor
            accuracyPaint.alpha = colorFactory.circleFillColorAlpha
            accuracyPaint.isAntiAlias = false
            accuracyPaint.style = Paint.Style.FILL
            canvas.drawCircle(
                curScreenCoords.x.toFloat(),
                curScreenCoords.y.toFloat(),
                accuracyRadius,
                accuracyPaint
            )

            /* Draw the edge. */
            accuracyPaint.color = colorFactory.circleEdgeColor
            accuracyPaint.alpha = colorFactory.circleEdgeColorAlpha
            accuracyPaint.isAntiAlias = true
            accuracyPaint.style = Paint.Style.STROKE
            canvas.drawCircle(
                curScreenCoords.x.toFloat(),
                curScreenCoords.y.toFloat(),
                accuracyRadius,
                accuracyPaint
            )
            true
        } else {
            false
        }


        return circleDrawn or super.onDrawItem(canvas, item, curScreenCoords, pProjection)
    }
}
