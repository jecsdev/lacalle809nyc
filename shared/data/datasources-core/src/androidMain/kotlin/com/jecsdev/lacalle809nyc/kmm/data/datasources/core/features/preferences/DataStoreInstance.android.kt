package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.features.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.features.preferences.createDataStore
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.features.preferences.dataStoreFileName

fun dataStore(context: Context): DataStore<Preferences> =
    createDataStore(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )
