package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.features.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.features.preferences.createDataStore
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.features.preferences.dataStoreFileName
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
fun dataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }
)
