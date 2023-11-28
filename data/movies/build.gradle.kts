plugins {
    id("popcornmovie.android.library")
    id("popcornmovie.android.hilt")
    id("kotlinx-serialization")
}

android {
    buildFeatures {
        buildConfig = true
    }

    namespace = "com.victor.data.movies"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:model"))

    implementation("androidx.paging:paging-runtime-ktx:3.2.0")
//    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}
