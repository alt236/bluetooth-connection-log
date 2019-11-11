package uk.co.alt236.bluetoothconnectionlog.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Suppress("PrivatePropertyName", "PropertyName")
internal class Migrations {

    private val MIGRATION_5_6: Migration = object : Migration(5, 6) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE log_entries ADD COLUMN `device_type` INTEGER NOT NULL DEFAULT '0'")
        }
    }


    val ALL_MIGRATIONS = arrayOf(MIGRATION_5_6)
}