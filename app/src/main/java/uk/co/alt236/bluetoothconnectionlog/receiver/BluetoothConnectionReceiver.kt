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
import androidx.preference.PreferenceManager
import com.google.android.gms.location.LocationServices
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.repo.LogRepository

private const val TAG = "BTCONEVENT"

class BluetoothConnectionReceiver : BroadcastReceiver() {
    private val mapper = DataMapper()

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        val device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) as BluetoothDevice
        val logRepository = LogRepository(context)

        when (action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> insert(context, logRepository, device, action)
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> insert(context, logRepository, device, action)
            BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED -> insert(context, logRepository, device, action)
            else -> return
        }
    }

    private fun insert(context: Context, logRepository: LogRepository, device: BluetoothDevice, action: String) {
        val logEntry = mapper.createLogEntry(device, action, System.currentTimeMillis())
        Log.d(TAG, "Device status changed: ${logEntry.event} - $device")
        writeEntry(context, logRepository, logEntry)
        notify(context, action, logEntry)
    }

    private fun notify(context: Context, action: String, logEntry: LogEntry) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        when (action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                if (preferences.getBoolean(context.getString(R.string.preference_key_notify_on_connection), false)) {
                    val text = "BT device connected: '${logEntry.getDisplayName()}'"
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                if (preferences.getBoolean(context.getString(R.string.preference_key_notify_on_disconnection), false)) {
                    val text = "BT device disconnected: '${logEntry.getDisplayName()}'"
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
            BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED -> {
                if (preferences.getBoolean(context.getString(R.string.preference_key_notify_on_disconnection), false)) {
                    val text = "BT device disconnect requested: '${logEntry.getDisplayName()}'"
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
            else -> return
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
        ) == PackageManager.PERMISSION_GRANTED);
    }
}
