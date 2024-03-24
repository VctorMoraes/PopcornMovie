plugins {
    id("popcornmovie.android.feature")
    id("popcornmovie.android.library.compose")
}

android {
    namespace = "com.victor.feature.home"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":domain:movies"))
    implementation(project(":domain:genres"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.drawerlayout)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.constraintLayout)
    implementation(libs.glide)
    implementation(libs.material)
    implementation(libs.androidx.paging.compose)
}
