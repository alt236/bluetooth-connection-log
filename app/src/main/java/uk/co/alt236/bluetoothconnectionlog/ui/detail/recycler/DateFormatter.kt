package uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler

import android.content.Context
import android.os.Build
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter(context: Context) {
    private val dateFormat = createDateFormat(context.applicationContext)

    private fun createDateFormat(context: Context): java.text.DateFormat {
        val currentLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }

        val pattern = DateFormat.getBestDateTimePattern(currentLocale, "c MMMM d HH mm ss yyyy")
        return SimpleDateFormat(pattern, currentLocale)
    }

    fun format(epoch: Long): String {
        return format(Date(epoch))
    }

    fun format(date: Date): String {
        return dateFormat.format(date)
    }
}