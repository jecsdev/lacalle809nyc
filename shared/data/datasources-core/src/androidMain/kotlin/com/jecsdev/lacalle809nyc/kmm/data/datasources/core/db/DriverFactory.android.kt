package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.db.Database
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.db.KmmDatabase
import org.koin.java.KoinJavaComponent.inject

actual class DriverFactory {
    private val context: Context by inject(Context::class.java)
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            KmmDatabase.Schema,
            this.context,
            Database.databaseName
        )
    }
}
