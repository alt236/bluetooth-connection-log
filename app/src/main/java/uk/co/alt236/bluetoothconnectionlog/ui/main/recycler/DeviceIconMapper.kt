package uk.co.alt236.bluetoothconnectionlog.ui.main.recycler

import android.bluetooth.BluetoothClass
import android.util.Log
import androidx.annotation.DrawableRes
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice

// These are @hidden in Android's BluetoothDevice class
private const val PERIPHERAL_KEYBOARD = 0x0540
private const val PERIPHERAL_POINTING = 0x0580
private const val PERIPHERAL_KEYBOARD_POINTING = 0x05C0

internal class DeviceIconMapper {
    @DrawableRes
    fun getImage(item: LogDevice): Int {
        return when (item.device_class.deviceClass) {
            BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO -> R.drawable.ic_device_car_32dp
            BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE -> R.drawable.ic_device_headset_mic_32dp
            BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES -> R.drawable.ic_device_headset_32dp
            BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO -> R.drawable.ic_device_music_32dp
            BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER -> R.drawable.ic_device_speaker_32dp
            BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET -> R.drawable.ic_device_headset_mic_32dp

            BluetoothClass.Device.COMPUTER_DESKTOP -> R.drawable.ic_device_desktop_32dp
            BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA -> R.drawable.ic_device_tablet_32dp
            BluetoothClass.Device.COMPUTER_LAPTOP -> R.drawable.ic_device_laptop_32dp
            BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA -> R.drawable.ic_device_tablet_32dp

            BluetoothClass.Device.PHONE_SMART -> R.drawable.ic_device_phone_smart_32dp

            BluetoothClass.Device.TOY_CONTROLLER -> R.drawable.ic_device_game_controller_32dp

            BluetoothClass.Device.WEARABLE_WRIST_WATCH -> R.drawable.ic_device_watch_32dp

            PERIPHERAL_KEYBOARD -> R.drawable.ic_device_keyboard_32dp
            PERIPHERAL_KEYBOARD_POINTING -> R.drawable.ic_device_keyboard_32dp
            PERIPHERAL_POINTING -> R.drawable.ic_device_mouse_32dp

            // Try to get a high level icon
            else -> getImageFromMajorClass(item)
        }
    }

    @DrawableRes
    private fun getImageFromMajorClass(item: LogDevice): Int {
        val deviceClass = item.device_class.deviceClass

        return when (val majorClass = item.device_class.majorDeviceClass) {
            BluetoothClass.Device.Major.HEALTH -> R.drawable.ic_device_health_32dp
            BluetoothClass.Device.Major.IMAGING -> R.drawable.ic_device_imaging_32dp
            BluetoothClass.Device.Major.NETWORKING -> R.drawable.ic_device_networking_32dp
            BluetoothClass.Device.Major.PERIPHERAL -> R.drawable.ic_device_peripheral_32dp
            BluetoothClass.Device.Major.PHONE -> R.drawable.ic_device_phone_32dp
            BluetoothClass.Device.Major.TOY -> R.drawable.ic_device_toy_32dp
            BluetoothClass.Device.Major.UNCATEGORIZED -> R.drawable.ic_device_bluetooth_32dp
            else -> {
                Log.d(
                    DeviceIconMapper::class.java.simpleName,
                    "Did not have an icon for '${item.device_name}' with class: ${deviceClass.toHexString()}/${majorClass.toHexString()}"
                )
                R.drawable.ic_device_bluetooth_32dp
            }
        }
    }

    private fun Int.toHexString(): String {
        return this.toString(16)
    }
}