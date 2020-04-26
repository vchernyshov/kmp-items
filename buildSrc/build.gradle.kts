plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    google()

    maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.71")
    implementation("com.android.tools.build:gradle:4.0.0-beta05")
    implementation("dev.icerock:mobile-multiplatform:0.6.1")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}