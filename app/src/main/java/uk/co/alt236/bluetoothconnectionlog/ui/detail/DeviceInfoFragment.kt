package uk.co.alt236.bluetoothconnectionlog.ui.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.BtDevice
import uk.co.alt236.bluetoothconnectionlog.extensions.getInfoFor
import uk.co.alt236.bluetoothconnectionlog.extensions.getSerializableOrThrow
import uk.co.alt236.btdeviceinfo.BtDeviceClassInfoRepo


class DeviceInfoFragment : DialogFragment() {
    private val classInfoRepo = BtDeviceClassInfoRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_device_info, null)
        populateView(view)

        val listener = DialogInterface.OnClickListener { _, _ -> }
        return AlertDialog.Builder(context!!)
            //.setTitle()
            .setPositiveButton(android.R.string.ok, listener)
            .setView(view)
            .create()
    }

    private fun populateView(view: View) {
        val btDevice = arguments.getSerializableOrThrow(ARG_DEVICE) as BtDevice
        Log.d(TAG, "Showing info for: $btDevice")

        val deviceNameRow = view.findViewById<View>(R.id.device_name)
        val deviceAddressRow = view.findViewById<View>(R.id.device_mac_address)
        val deviceTypeRow = view.findViewById<View>(R.id.device_type)
        val deviceClassRow = view.findViewById<View>(R.id.device_class)
        val deviceClassHexRow = view.findViewById<View>(R.id.device_class_hex)
        val deviceMajorClassRow = view.findViewById<View>(R.id.device_major_class)
        val deviceMajorClassHexRow = view.findViewById<View>(R.id.device_major_class_hex)

        val deviceClassInfo = classInfoRepo.getInfoFor(btDevice)

        // OVERVIEW
        setSeparatorData(view.findViewById(R.id.overview_separator), "Overview")
        setRowData(deviceNameRow, "Name:", btDevice.name)
        setRowData(deviceAddressRow, "MAC:", btDevice.macAddress)
        setRowData(deviceTypeRow, "Type:", btDevice.type.name)

        // DEVICE CLASS
        setSeparatorData(view.findViewById(R.id.device_class_separator), "Device Class")
        setRowData(deviceClassRow, "Name:", deviceClassInfo.deviceClassNameResId)
        setRowData(deviceClassHexRow, "Value:", deviceClassInfo.deviceClass.toHex())

        // DEVICE MAJOR CLASS
        setSeparatorData(view.findViewById(R.id.device_major_class_separator), "Device Major Class")
        setRowData(deviceMajorClassRow, "Name:", deviceClassInfo.majorDeviceClassNameResId)
        setRowData(deviceMajorClassHexRow, "Value:", deviceClassInfo.majorDeviceClass.toHex())
    }

    private fun setRowData(row: View, title: String, value: String) {
        row.findViewById<TextView>(R.id.title).text = title
        row.findViewById<TextView>(R.id.value).text = value
    }

    private fun setRowData(row: View, title: String, @StringRes value: Int) {
        row.findViewById<TextView>(R.id.title).text = title
        row.findViewById<TextView>(R.id.value).setText(value)
    }
    
    private fun setSeparatorData(row: View, title: String) {
        row.findViewById<TextView>(R.id.title).text = title
    }

    private fun Int.toHex(): String {
        return String.format("0x%04X", this)
    }

    companion object {
        private const val ARG_DEVICE = "ARG_DEVICE"
        private val TAG = DeviceInfoFragment::class.java.simpleName

        fun createInstance(btDevice: BtDevice): DialogFragment {
            val fragment = DeviceInfoFragment()
            val args = Bundle()

            args.putSerializable(ARG_DEVICE, btDevice)

            fragment.arguments = args
            return fragment

        }
    }
}