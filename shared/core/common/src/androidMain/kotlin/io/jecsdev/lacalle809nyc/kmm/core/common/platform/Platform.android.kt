package io.jecsdev.lacalle809nyc.kmm.core.common.platform

import android.os.Build
import io.jecsdev.lacalle809nyc.kmm.core.common.platform.entities.PlatformData
import io.jecsdev.lacalle809nyc.kmm.core.common.platform.enums.PlatformType


class AndroidPlatform : Platform {
    override val platformData: PlatformData
        get() = PlatformData(
            platformType = PlatformType.ANDROID,
            osVersion = Build.VERSION.SDK_INT.toString()
        )
}

actual fun getPlatform(): Platform = AndroidPlatform()