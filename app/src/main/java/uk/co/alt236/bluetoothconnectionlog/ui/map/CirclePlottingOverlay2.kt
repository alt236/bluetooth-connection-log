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
}
