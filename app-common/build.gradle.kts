plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
}

android {
    compileSdkVersion(Versions.Android.compileSdk)

    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)
    }
}

setupFramework(exports = listOf(Deps.Libs.MultiPlatform.items))

dependencies {
    mppModule(Deps.Libs.MultiPlatform.items)
    mppLibrary(Deps.Libs.MultiPlatform.kotlinStdLib)
    androidLibrary(Deps.Libs.Android.appCompat)
}