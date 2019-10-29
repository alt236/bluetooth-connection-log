package uk.co.alt236.bluetoothconnectionlog.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import uk.co.alt236.bluetoothconnectionlog.db.entities.Event
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.prefs.NotificationPrefs
import uk.co.alt236.bluetoothconnectionlog.repo.favourites.FavouritesRepository
import uk.co.alt236.bluetoothconnectionlog.repo.log.LogRepository

private const val TAG = "BTCONEVENT"

class BluetoothConnectionReceiver : BroadcastReceiver() {
    private val mapper = DataMapper()
    private val stringResResolver = ToastStringResIdResolver()

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return

        // The -1 is for the UNKNOWN state
        check(HANDLED_ACTIONS.size == Event.values().size - 1) {
            "There seems to be disparity between the handled states and the event enum..."
        }

        if (HANDLED_ACTIONS.contains(action)) {
            val logRepository = LogRepository(context)
            val isFaRepo =
                FavouritesRepository(context)

            val device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) as BluetoothDevice
            val logEntry = mapper.createLogEntry(device, action, System.currentTimeMillis())
            val isFavourite = isFaRepo.isFavourite(logEntry.device)

            writeEntry(context, logRepository, logEntry)
            notify(context, logEntry, isFavourite)
        }
    }

    private fun notify(context: Context, logEntry: LogEntry, isFav: Boolean) {
        val prefs = NotificationPrefs(context)

        if (prefs.shouldNotifyOnlyForFavs() && !isFav) {
            return
        }

        val deviceName = logEntry.getDisplayName()

        when (logEntry.event) {
            Event.CONNECTED -> {
                if (prefs.shouldNotifyOnConnection()) {
                    val resId = stringResResolver.getConnectionResId(isFav)
                    val text = context.getString(resId, deviceName)
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
            Event.DISCONNECTED -> {
                if (prefs.shouldNotifyOnDisconnection()) {
                    val resId = stringResResolver.getDisconnectionResId(isFav)
                    val text = context.getString(resId, deviceName)
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
            Event.DISCONNECT_REQUESTED -> {
                if (prefs.shouldNotifyOnDisconnectionRequest()) {
                    val resId = stringResResolver.getDisconnectionRequestedResId(isFav)
                    val text = context.getString(resId, deviceName)
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
            Event.UNKNOWN -> {
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun writeEntry(context: Context, logRepository: LogRepository, logEntry: LogEntry) {
        if (hasLocationPermissions(context)) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location == null) {
                    Log.w(TAG, "Location was null")
                    logRepository.insert(logEntry)
                } else {
                    Log.d(TAG, "Got a valid location")
                    val newEntry = mapper.updateWithLocation(logEntry, location)
                    logRepository.insert(newEntry)
                }
            }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Failed to connect to location provider: $exception")
                    logRepository.insert(logEntry)
                }
        } else {
            Log.d(TAG, "Did not have location permissions.")
            logRepository.insert(logEntry)
        }
    }

    private fun hasLocationPermissions(context: Context): Boolean {
        return (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private companion object {
        val HANDLED_ACTIONS = setOf(
            BluetoothDevice.ACTION_ACL_CONNECTED,
            BluetoothDevice.ACTION_ACL_DISCONNECTED,
            BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED
        )
    }
}
