package uk.co.alt236.btdeviceinfo.mappers

import androidx.annotation.DrawableRes
import uk.co.alt236.btdeviceinfo.R
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceClass
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceMajorClass

internal class IconMapper {

    @DrawableRes
    fun getImage(deviceClass: ParsedDeviceClass, majorDeviceClass: ParsedDeviceMajorClass): Int {
        return when (deviceClass) {
            ParsedDeviceClass.AUDIO_VIDEO_CAR_AUDIO -> R.drawable.ic_device_car_32dp
            ParsedDeviceClass.AUDIO_VIDEO_HANDSFREE -> R.drawable.ic_device_headset_mic_32dp
            ParsedDeviceClass.AUDIO_VIDEO_HEADPHONES -> R.drawable.ic_device_headset_32dp
            ParsedDeviceClass.AUDIO_VIDEO_HIFI_AUDIO -> R.drawable.ic_device_music_32dp
            ParsedDeviceClass.AUDIO_VIDEO_LOUDSPEAKER -> R.drawable.ic_device_speaker_32dp
            ParsedDeviceClass.AUDIO_VIDEO_WEARABLE_HEADSET -> R.drawable.ic_device_headset_mic_32dp

            ParsedDeviceClass.COMPUTER_DESKTOP -> R.drawable.ic_device_desktop_32dp
            ParsedDeviceClass.COMPUTER_HANDHELD_PC_PDA -> R.drawable.ic_device_tablet_32dp
            ParsedDeviceClass.COMPUTER_LAPTOP -> R.drawable.ic_device_laptop_32dp
            ParsedDeviceClass.COMPUTER_PALM_SIZE_PC_PDA -> R.drawable.ic_device_tablet_32dp

            ParsedDeviceClass.PHONE_SMART -> R.drawable.ic_device_phone_smart_32dp

            ParsedDeviceClass.TOY_CONTROLLER -> R.drawable.ic_device_game_controller_32dp

            ParsedDeviceClass.WEARABLE_WRIST_WATCH -> R.drawable.ic_device_watch_32dp

            ParsedDeviceClass.PERIPHERAL_KEYBOARD -> R.drawable.ic_device_keyboard_32dp
            ParsedDeviceClass.PERIPHERAL_KEYBOARD_POINTING -> R.drawable.ic_device_keyboard_32dp
            ParsedDeviceClass.PERIPHERAL_POINTING -> R.drawable.ic_device_mouse_32dp

            else -> getImageFromMajorClass(majorDeviceClass)
        }
    }

    @DrawableRes
    private fun getImageFromMajorClass(majorDeviceClass: ParsedDeviceMajorClass): Int {
        return when (majorDeviceClass) {
            ParsedDeviceMajorClass.HEALTH -> R.drawable.ic_device_health_32dp
            ParsedDeviceMajorClass.IMAGING -> R.drawable.ic_device_imaging_32dp
            ParsedDeviceMajorClass.NETWORKING -> R.drawable.ic_device_networking_32dp
            ParsedDeviceMajorClass.PERIPHERAL -> R.drawable.ic_device_peripheral_32dp
            ParsedDeviceMajorClass.PHONE -> R.drawable.ic_device_phone_32dp
            ParsedDeviceMajorClass.TOY -> R.drawable.ic_device_toy_32dp
            ParsedDeviceMajorClass.UNCATEGORIZED -> R.drawable.ic_device_bluetooth_32dp

            ParsedDeviceMajorClass.MISC -> FALLBACK_ICON
            ParsedDeviceMajorClass.COMPUTER -> FALLBACK_ICON
            ParsedDeviceMajorClass.AUDIO_VIDEO -> FALLBACK_ICON
            ParsedDeviceMajorClass.WEARABLE -> FALLBACK_ICON

            ParsedDeviceMajorClass.UNKNOWN -> FALLBACK_ICON
        }
    }

    private companion object {
        @DrawableRes
        val FALLBACK_ICON = R.drawable.ic_device_bluetooth_32dp
    }
}