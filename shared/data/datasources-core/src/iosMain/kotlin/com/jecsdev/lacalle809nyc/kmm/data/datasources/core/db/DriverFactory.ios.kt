package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.db.Database
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.db.KmmDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            KmmDatabase.Schema,
            Database.databaseName
        )
    }
}
