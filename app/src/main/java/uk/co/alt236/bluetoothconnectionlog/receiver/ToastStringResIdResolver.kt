package uk.co.alt236.bluetoothconnectionlog.receiver

import androidx.annotation.StringRes
import uk.co.alt236.bluetoothconnectionlog.R

internal class ToastStringResIdResolver {

    @StringRes
    fun getConnectionResId(isFav: Boolean): Int {
        return if (isFav) {
            R.string.toast_fav_device_connected
        } else {
            R.string.toast_device_connected
        }
    }

    @StringRes
    fun getDisconnectionResId(isFav: Boolean): Int {
        return if (isFav) {
            R.string.toast_fav_device_disconnected
        } else {
            R.string.toast_device_disconnected
        }
    }

    @StringRes
    fun getDisconnectionRequestedResId(isFav: Boolean): Int {
        return if (isFav) {
            R.string.toast_fav_device_disconnect_requested
        } else {
            R.string.toast_device_disconnect_requested
        }
    }

}