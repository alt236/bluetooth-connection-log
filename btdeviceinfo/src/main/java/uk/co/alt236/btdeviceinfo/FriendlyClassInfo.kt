@file:Suppress("unused")

package uk.co.alt236.btdeviceinfo

import androidx.annotation.DrawableRes
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceClass
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceMajorClass

class FriendlyClassInfo(
    @DrawableRes val iconRes: Int,
    val deviceClass: Int,
    val majorDeviceClass: Int,
    val parsedDeviceClass: ParsedDeviceClass,
    val parsedMajorDeviceClass: ParsedDeviceMajorClass
)