package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.db

import app.cash.sqldelight.db.SqlDriver
import com.jecsdev.lacalle809nyc.data.datasources.core.db.LaCalleDb

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): LaCalleDb {
    val driver = driverFactory.createDriver()
    val database = LaCalleDb(driver)

    // Do more work with the database (see below).
    return database
}
