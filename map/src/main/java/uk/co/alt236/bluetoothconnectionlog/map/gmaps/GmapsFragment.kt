package uk.co.alt236.bluetoothconnectionlog.map.gmaps

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import uk.co.alt236.bluetoothconnectionlog.map.LocationPermissionCheck
import uk.co.alt236.bluetoothconnectionlog.map.R
import uk.co.alt236.bluetoothconnectionlog.map.model.Poi


internal class GmapsFragment : Fragment() {

    private lateinit var mapView: MapView
    private val locationPermissionCheck = LocationPermissionCheck()
    private val markerDrawer = MarkerDrawer()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_map_google, container, false)

        mapView = v.findViewById(R.id.map) as MapView
        mapView.onCreate(savedInstanceState)

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.activity)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.getMapAsync { map ->
            val poi: Poi = arguments?.getSerializable(ARG_POI) as Poi

            if (locationPermissionCheck.isLocationAccessGranted(context!!)) {
                map.uiSettings.isMyLocationButtonEnabled = true
                map.isMyLocationEnabled = true
            }

            markerDrawer.drawMarker(view.context, map, poi)
            goToLocation(map, poi)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_google_maps, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_center_on_marker -> {
                mapView.getMapAsync { map ->
                    val poi: Poi = arguments?.getSerializable(ARG_POI) as Poi
                    goToLocation(map, poi)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToLocation(map: GoogleMap, poi: Poi) {
        val latLng = LatLng(poi.latitude, poi.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM)
        map.animateCamera(cameraUpdate)
    }

    override fun onStart() {
        mapView.onStart()
        super.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onDetach() {
        super.onDetach()
        mapView.onDestroy()
    }

    companion object {
        private const val ARG_POI = "ARG_POI"
        private const val DEFAULT_ZOOM = 18f

        fun createFragment(poi: Poi): Fragment {
            val fragment = GmapsFragment()
            val args = Bundle()
            args.putSerializable(ARG_POI, poi)

            fragment.arguments = args

            return fragment
        }
    }
}