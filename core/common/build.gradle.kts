plugins {
    id("popcornmovie.android.library")
    id("popcornmovie.android.hilt")
}

android {
    namespace = "com.victor.core.commom"
}
dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.viewbinding)
}

