@file:Suppress("unused")

package uk.co.alt236.btdeviceinfo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceClass
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceMajorClass

class FriendlyClassInfo(
    @DrawableRes val iconRes: Int,
    val deviceClass: Int,
    val majorDeviceClass: Int,
    @StringRes val deviceClassNameResId: Int,
    @StringRes val majorDeviceClassNameResId: Int,
    val parsedDeviceClass: ParsedDeviceClass,
    val parsedMajorDeviceClass: ParsedDeviceMajorClass
)