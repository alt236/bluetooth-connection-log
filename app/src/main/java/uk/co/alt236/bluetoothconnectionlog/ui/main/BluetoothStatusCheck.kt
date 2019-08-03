package uk.co.alt236.bluetoothconnectionlog.ui.main

import android.bluetooth.BluetoothManager
import android.content.Context

class BluetoothStatusCheck(context: Context) {
    private val btManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
    private val btAdapter = btManager?.adapter

    fun isBluetoothOn(): Boolean {
        return btAdapter?.isEnabled ?: false
    }
}