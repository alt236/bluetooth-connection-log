package uk.co.alt236.bluetoothconnectionlog.extensions

import android.os.Bundle
import java.io.Serializable


fun Bundle?.getSerializableOrThrow(key: String): Serializable {
    checkNotNull(this) { "Bundle is null!" }

    return getSerializable(key)
        ?: throw IllegalArgumentException("No value for '$key' was provided.")
}