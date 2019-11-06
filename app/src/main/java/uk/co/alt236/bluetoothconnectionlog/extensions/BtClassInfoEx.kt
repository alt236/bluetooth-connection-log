package uk.co.alt236.bluetoothconnectionlog.extensions

import uk.co.alt236.bluetoothconnectionlog.db.entities.BtDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.btdeviceinfo.BtDeviceClassInfoRepo
import uk.co.alt236.btdeviceinfo.FriendlyClassInfo


fun BtDeviceClassInfoRepo.getInfoFor(item: LogDevice): FriendlyClassInfo {
    return getInfoFor(item.device)
}

fun BtDeviceClassInfoRepo.getInfoFor(item: BtDevice): FriendlyClassInfo {
    val deviceClass = item.bluetoothClass
    return getInfoFor(deviceClass.deviceClass, deviceClass.majorDeviceClass)
}