plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = BuildVersion.environment.applicationId
    compileSdk = BuildVersion.android.compileSdk
    defaultConfig {
        minSdk = BuildVersion.android.minSdk
        targetSdk = BuildVersion.android.targetSdk
        applicationId = BuildVersion.environment.applicationId
        versionCode = BuildVersion.environment.appVersion
        versionName = BuildVersion.environment.appVersionCode
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            isDebuggable = true
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

    }
    compileOptions {
        sourceCompatibility = BuildVersion.environment.javaVersion
        targetCompatibility = BuildVersion.environment.javaVersion
    }
    kotlinOptions {
        jvmTarget = BuildVersion.environment.jvmTarget
    }
}

dependencies {
    implementation(projects.shared.presentation.ui)
    implementation(projects.shared.core.common)
    implementation(project.dependencies.platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.runtime.android)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.activityCompose)
    implementation(libs.bundles.android.core)
    debugImplementation(libs.compose.uitooling)
}

tasks.register("testClasses") {
    println("Dummy classes")
}