plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlinx-serialization")
}

android {
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    namespace = "com.gahov.data"
}

dependencies {

    dependencies {
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")

        implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.7")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")

        implementation("com.google.code.gson:gson:2.10.1")

        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
        implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

        implementation("com.google.dagger:hilt-android:2.47")
        kapt("com.google.dagger:hilt-android-compiler:2.47")

        implementation("androidx.room:room-runtime:2.6.1")
        kapt ("androidx.room:room-compiler:2.6.1")

        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
        implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")


        implementation(project(":domain"))
    }
}