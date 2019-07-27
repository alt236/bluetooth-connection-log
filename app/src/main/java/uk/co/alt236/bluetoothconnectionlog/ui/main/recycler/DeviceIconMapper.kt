package uk.co.alt236.bluetoothconnectionlog.ui.main.recycler

import androidx.annotation.DrawableRes
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.DeviceClass
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice

internal class DeviceIconMapper {
    @DrawableRes
    fun getImage(item: LogDevice): Int {
        return when (item.device_class) {
            DeviceClass.AUDIO_VIDEO_CAR_AUDIO -> R.drawable.ic_device_car_32dp
            DeviceClass.AUDIO_VIDEO_HANDSFREE -> R.drawable.ic_device_headset_mic_32dp
            DeviceClass.AUDIO_VIDEO_HEADPHONES -> R.drawable.ic_device_headset_32dp
            DeviceClass.AUDIO_VIDEO_HIFI_AUDIO -> R.drawable.ic_device_music_32dp
            DeviceClass.AUDIO_VIDEO_LOUDSPEAKER -> R.drawable.ic_device_speaker_32dp
            DeviceClass.AUDIO_VIDEO_WEARABLE_HEADSET -> R.drawable.ic_device_headset_mic_32dp

            DeviceClass.COMPUTER_DESKTOP -> R.drawable.ic_device_desktop_32dp
            DeviceClass.COMPUTER_HANDHELD_PC_PDA -> R.drawable.ic_device_tablet_32dp
            DeviceClass.COMPUTER_LAPTOP -> R.drawable.ic_device_laptop_32dp
            DeviceClass.COMPUTER_PALM_SIZE_PC_PDA -> R.drawable.ic_device_tablet_32dp

            DeviceClass.PHONE_UNCATEGORIZED -> R.drawable.ic_device_phone_32dp
            DeviceClass.PHONE_SMART -> R.drawable.ic_device_phone_32dp

            DeviceClass.TOY_CONTROLLER -> R.drawable.ic_device_game_controller_32dp
            DeviceClass.WEARABLE_WRIST_WATCH -> R.drawable.ic_device_watch_32dp

            else -> R.drawable.ic_device_bluetooth_32dp
        }
    }
}