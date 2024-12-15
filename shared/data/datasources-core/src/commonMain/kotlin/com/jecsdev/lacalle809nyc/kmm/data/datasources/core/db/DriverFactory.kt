package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.db

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): LaCalleDb {
    val driver = driverFactory.createDriver()
    val database = LaCalleDb(driver)

    // Do more work with the database (see below).
    return database
}
