package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST

interface ApiService {
    @GET("/api/values")
    suspend fun getAllItems(): List<String>

    @POST("/api/values")
    suspend fun putAllItems(@Body items: List<String>)

    companion object {
        const val SOURCE_URL = "https://app.sonicpanelradio.com/8082/stream"
    }
}
