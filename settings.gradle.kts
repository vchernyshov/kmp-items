pluginManagement {
    repositories {
        jcenter()
        google()

        maven { url = uri("https://dl.bintray.com/kotlin/kotlin") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://jetbrains.bintray.com/kotlin-native-dependencies") }
    }
}

enableFeaturePreview("GRADLE_METADATA")

include(":items")
include(":app-common")
include(":app-android")

rootProject.name = "kmp-items"