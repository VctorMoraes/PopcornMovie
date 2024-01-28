plugins {
    id("popcornmovie.android.library")
    id("popcornmovie.android.hilt")
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
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.drawerlayout)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.constraintLayout)
    implementation(libs.glide)
    implementation(libs.material)
}
