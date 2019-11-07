package uk.co.alt236.btdeviceinfo.mappers

import androidx.annotation.StringRes
import uk.co.alt236.btdeviceinfo.R
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceClass
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceClass.*
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceMajorClass

internal class TextMapper {

    @StringRes
    fun getName(deviceClass: ParsedDeviceClass): Int {
        return when (deviceClass) {
            UNKNOWN -> R.string.bt_device_class_unknown
            AUDIO_VIDEO_CAMCORDER -> R.string.bt_device_class_audio_video_camcorder
            AUDIO_VIDEO_CAR_AUDIO -> R.string.bt_device_class_audio_video_car_audio
            AUDIO_VIDEO_HANDSFREE -> R.string.bt_device_class_audio_video_handsfree
            AUDIO_VIDEO_HEADPHONES -> R.string.bt_device_class_audio_video_headphones
            AUDIO_VIDEO_HIFI_AUDIO -> R.string.bt_device_class_audio_video_hifi_audio
            AUDIO_VIDEO_LOUDSPEAKER -> R.string.bt_device_class_audio_video_loudspeaker
            AUDIO_VIDEO_MICROPHONE -> R.string.bt_device_class_audio_video_microphone
            AUDIO_VIDEO_PORTABLE_AUDIO -> R.string.bt_device_class_audio_video_portable_audio
            AUDIO_VIDEO_RESERVED_1 -> R.string.bt_device_class_audio_video_reserved
            AUDIO_VIDEO_RESERVED_2 -> R.string.bt_device_class_audio_video_reserved
            AUDIO_VIDEO_SET_TOP_BOX -> R.string.bt_device_class_audio_video_set_top_box
            AUDIO_VIDEO_UNCATEGORIZED -> R.string.bt_device_class_audio_video_uncategorized
            AUDIO_VIDEO_VCR -> R.string.bt_device_class_audio_video_vcr
            AUDIO_VIDEO_VIDEO_CAMERA -> R.string.bt_device_class_audio_video_video_camera
            AUDIO_VIDEO_VIDEO_CONFERENCING -> R.string.bt_device_class_audio_video_video_conferencing
            AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER -> R.string.bt_device_class_audio_video_video_display_and_loudspeaker
            AUDIO_VIDEO_VIDEO_GAMING_TOY -> R.string.bt_device_class_audio_video_video_gaming_toy
            AUDIO_VIDEO_VIDEO_MONITOR -> R.string.bt_device_class_audio_video_video_monitor
            AUDIO_VIDEO_WEARABLE_HEADSET -> R.string.bt_device_class_audio_video_wearable_headset

            COMPUTER_DESKTOP -> R.string.bt_device_class_computer_desktop
            COMPUTER_HANDHELD_PC_PDA -> R.string.bt_device_class_computer_handheld_pc_pda
            COMPUTER_LAPTOP -> R.string.bt_device_class_computer_laptop
            COMPUTER_PALM_SIZE_PC_PDA -> R.string.bt_device_class_computer_palm_size_pc_pda
            COMPUTER_SERVER -> R.string.bt_device_class_computer_server
            COMPUTER_UNCATEGORIZED -> R.string.bt_device_class_computer_uncategorized
            COMPUTER_WEARABLE -> R.string.bt_device_class_computer_wearable

            HEALTH_BLOOD_PRESSURE -> R.string.bt_device_class_health_blood_pressure
            HEALTH_DATA_DISPLAY -> R.string.bt_device_class_health_data_display
            HEALTH_GLUCOSE -> R.string.bt_device_class_health_glucose
            HEALTH_PULSE_OXIMETER -> R.string.bt_device_class_health_pulse_oximeter
            HEALTH_PULSE_RATE -> R.string.bt_device_class_health_pulse_rate
            HEALTH_THERMOMETER -> R.string.bt_device_class_health_thermometer
            HEALTH_UNCATEGORIZED -> R.string.bt_device_class_health_uncategorized
            HEALTH_WEIGHING -> R.string.bt_device_class_health_weighing

            PERIPHERAL_KEYBOARD -> R.string.bt_device_class_peripheral_keyboard
            PERIPHERAL_KEYBOARD_POINTING -> R.string.bt_device_class_peripheral_keyboard_pointing
            PERIPHERAL_POINTING -> R.string.bt_device_class_peripheral_pointing
            PERIPHERAL_UNCATEGORIZED -> R.string.bt_device_class_peripheral_uncategorized

            PHONE_CELLULAR -> R.string.bt_device_class_phone_cellular
            PHONE_CORDLESS -> R.string.bt_device_class_phone_cordless
            PHONE_ISDN -> R.string.bt_device_class_phone_isdn
            PHONE_MODEM_OR_GATEWAY -> R.string.bt_device_class_phone_modem_or_gateway
            PHONE_SMART -> R.string.bt_device_class_phone_smart
            PHONE_UNCATEGORIZED -> R.string.bt_device_class_phone_uncategorized

            TOY_CONTROLLER -> R.string.bt_device_class_toy_controller
            TOY_DOLL_ACTION_FIGURE -> R.string.bt_device_class_toy_doll_action_figure
            TOY_GAME -> R.string.bt_device_class_toy_game
            TOY_ROBOT -> R.string.bt_device_class_toy_robot
            TOY_UNCATEGORIZED -> R.string.bt_device_class_toy_uncategorized
            TOY_VEHICLE -> R.string.bt_device_class_toy_vehicle

            WEARABLE_GLASSES -> R.string.bt_device_class_wearable_glasses
            WEARABLE_HELMET -> R.string.bt_device_class_wearable_helmet
            WEARABLE_JACKET -> R.string.bt_device_class_wearable_jacket
            WEARABLE_PAGER -> R.string.bt_device_class_wearable_pager
            WEARABLE_UNCATEGORIZED -> R.string.bt_device_class_wearable_uncategorized
            WEARABLE_WRIST_WATCH -> R.string.bt_device_class_wearable_wrist_watch
        }
    }

    @StringRes
    fun getName(majorDeviceClass: ParsedDeviceMajorClass): Int {
        return when (majorDeviceClass) {
            ParsedDeviceMajorClass.UNKNOWN -> R.string.bt_device_major_class_unknown
            ParsedDeviceMajorClass.MISC -> R.string.bt_device_major_class_misc
            ParsedDeviceMajorClass.COMPUTER -> R.string.bt_device_major_class_computer
            ParsedDeviceMajorClass.PHONE -> R.string.bt_device_major_class_phone
            ParsedDeviceMajorClass.NETWORKING -> R.string.bt_device_major_class_networking
            ParsedDeviceMajorClass.AUDIO_VIDEO -> R.string.bt_device_major_class_audio_video
            ParsedDeviceMajorClass.PERIPHERAL -> R.string.bt_device_major_class_peripheral
            ParsedDeviceMajorClass.IMAGING -> R.string.bt_device_major_class_imaging
            ParsedDeviceMajorClass.WEARABLE -> R.string.bt_device_major_class_wearable
            ParsedDeviceMajorClass.TOY -> R.string.bt_device_major_class_toy
            ParsedDeviceMajorClass.HEALTH -> R.string.bt_device_major_class_health
            ParsedDeviceMajorClass.UNCATEGORIZED -> R.string.bt_device_major_class_uncategorized
        }
    }
}