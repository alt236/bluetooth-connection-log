package uk.co.alt236.bluetoothconnectionlog.map.osm

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import uk.co.alt236.bluetoothconnectionlog.map.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.map.R
import uk.co.alt236.bluetoothconnectionlog.map.model.Poi

internal class OsmFragment : Fragment() {


    private lateinit var mapWrapper: MapWrapper

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Configuration.getInstance().load(
            context,
            context.getSharedPreferences(MAP_PREFERENCES, Context.MODE_PRIVATE)
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map_osm, container, false)
        val map = view.findViewById<MapView>(R.id.map)

        mapWrapper = MapWrapper(map)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            val poi: Poi = getSerializable(ARG_POI) as Poi

            Log.d(TAG, "Will display ${poi.latitude}/${poi.longitude}/${poi.accuracy}")

            mapWrapper.centerOn(poi)
        }
    }

    override fun onResume() {
        super.onResume()
        mapWrapper.onResume()
    }

    override fun onPause() {
        mapWrapper.onPause()
        super.onPause()
    }

    override fun onDetach() {
        mapWrapper.onDetach()
        super.onDetach()
    }

    companion object {
        private val TAG = OsmFragment::class.java.simpleName
        private const val ARG_POI = "ARG_POI"
        private const val MAP_PREFERENCES =
            BuildConfig.LIBRARY_PACKAGE_NAME + ".OSM_MAP_PREFERENCES"

        fun createFragment(poi: Poi): Fragment {
            val fragment = OsmFragment()
            val args = Bundle()
            args.putSerializable(ARG_POI, poi)

            fragment.arguments = args

            return fragment
        }
    }
}