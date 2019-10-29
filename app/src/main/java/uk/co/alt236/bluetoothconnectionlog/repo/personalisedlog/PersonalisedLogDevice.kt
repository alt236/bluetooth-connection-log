package uk.co.alt236.bluetoothconnectionlog.repo.personalisedlog

import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice

data class PersonalisedLogDevice(val logDevice: LogDevice, val favourite: Boolean)