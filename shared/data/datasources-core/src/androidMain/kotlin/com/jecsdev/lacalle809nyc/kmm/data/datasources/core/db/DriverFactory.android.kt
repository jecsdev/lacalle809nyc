package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jecsdev.lacalle809nyc.data.datasources.core.db.LaCalleDb
import org.koin.java.KoinJavaComponent.inject

actual class DriverFactory {
    private val context: Context by inject(Context::class.java)
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            LaCalleDb.Schema,
            this.context,
            Database.databaseName
        )
    }
}
