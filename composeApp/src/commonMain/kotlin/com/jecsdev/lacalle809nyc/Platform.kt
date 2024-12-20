package com.jecsdev.lacalle809nyc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform