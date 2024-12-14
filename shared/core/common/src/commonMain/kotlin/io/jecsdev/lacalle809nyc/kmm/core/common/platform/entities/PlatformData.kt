package io.jecsdev.lacalle809nyc.kmm.core.common.platform.entities

import io.jecsdev.lacalle809nyc.kmm.core.common.platform.enums.PlatformType

data class PlatformData(
    val platformType: PlatformType,
    val osVersion: String
) {
    override fun toString(): String = "${platformType.name} v. $osVersion"
}