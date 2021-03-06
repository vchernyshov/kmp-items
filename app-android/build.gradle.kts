plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdkVersion(Versions.Android.compileSdk)

    defaultConfig {
        applicationId = "dev.garage.items.app"
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }
}

dependencies {
    implementation(Deps.Libs.MultiPlatform.kotlinStdLib.android!!)

    implementation(project(Deps.Libs.MultiPlatform.items.name))
    implementation(project(Deps.Libs.MultiPlatform.appCommon.name))

    implementation(Deps.Libs.Android.appCompat.name)
    implementation(Deps.Libs.Android.material.name)
    implementation(Deps.Libs.Android.coreKtx.name)
    implementation(Deps.Libs.Android.constraint.name)
    implementation(Deps.Libs.Android.timber.name)
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("com.pes.materialcolorpicker:library:1.2.0")
}