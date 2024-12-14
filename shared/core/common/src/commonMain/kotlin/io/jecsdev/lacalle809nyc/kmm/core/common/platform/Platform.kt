package io.jecsdev.lacalle809nyc.kmm.core.common.platform

import io.jecsdev.lacalle809nyc.kmm.core.common.platform.entities.PlatformData

interface Platform {
    val platformData: PlatformData
}

expect fun getPlatform(): Platform