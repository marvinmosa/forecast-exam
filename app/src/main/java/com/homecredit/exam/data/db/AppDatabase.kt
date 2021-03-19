package com.homecredit.exam.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.homecredit.exam.data.db.ForecastDao
import com.homecredit.exam.data.model.ForecastItem
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteDatabaseHook
import net.sqlcipher.database.SupportFactory


@Database(entities = [ForecastItem::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao

    companion object {
        @Volatile
        private var forecastDb: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return forecastDb ?: synchronized(this) {
                forecastDb ?: buildDatabase(context).also { forecastDb = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            val dbname = "forecast"

            val builder = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "${dbname}.db"
            )

//            val passphrase: ByteArray = SQLiteDatabase.getBytes("P@s5P4ras3VeryL0n9".toCharArray())
//            val factory = SupportFactory(passphrase, object : SQLiteDatabaseHook {
//                override fun preKey(database: SQLiteDatabase?) = Unit
//                override fun postKey(database: SQLiteDatabase?) {
//                    database?.rawExecSQL("PRAGMA cipher_memory_security = ON")
//                }
//            })
//            builder.openHelperFactory(factory)
            return builder.build()
        }
    }
}
