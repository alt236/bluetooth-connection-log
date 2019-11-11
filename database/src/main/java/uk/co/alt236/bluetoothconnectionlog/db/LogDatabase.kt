package uk.co.alt236.bluetoothconnectionlog.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import uk.co.alt236.bluetoothconnectionlog.db.converters.DeviceClassTypeConverter
import uk.co.alt236.bluetoothconnectionlog.db.converters.DeviceTypeConverter
import uk.co.alt236.bluetoothconnectionlog.db.converters.EventTypeConverter
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry

private const val DB_NAME = "bt_connection_events"

@Database(entities = [LogEntry::class], version = 6)
@TypeConverters(
    EventTypeConverter::class,
    DeviceClassTypeConverter::class,
    DeviceTypeConverter::class
)
abstract class LogDatabase : RoomDatabase() {


    abstract fun entryDao(): LogEntryDao

    companion object {
        private var instance: LogDatabase? = null
        private val migrations = Migrations()

        fun getInstance(context: Context): LogDatabase {
            if (instance == null) {
                synchronized(LogDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LogDatabase::class.java, DB_NAME
                    )
                        .addMigrations(*migrations.ALL_MIGRATIONS)
                        //.fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }

    }
}