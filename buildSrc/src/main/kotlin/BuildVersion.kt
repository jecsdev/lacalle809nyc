import org.gradle.api.JavaVersion

object BuildVersion {
    object environment {
        const val applicationId = "com.jecsdev.lacalle809nyc"
        const val appName = "La calle 809 NYC"
        const val appVersion = 1
        const val appVersionCode = "1.0.0"
        val javaVersion = JavaVersion.VERSION_17
        const val jvmTarget = "17"
        const val appDatabaseName = "LaCalleDb"
    }

    object android {
        const val minSdk = 26
        const val compileSdk = 35
        const val targetSdk = 35
    }
}