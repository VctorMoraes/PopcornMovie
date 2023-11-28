plugins {
    id("popcornmovie.android.library")
    id("popcornmovie.android.hilt")
    id("kotlinx-serialization")
}

android {
    buildFeatures {
        buildConfig = true
    }

    namespace = "com.victor.data.genres"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}
