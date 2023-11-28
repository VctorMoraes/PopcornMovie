plugins {
    id("popcornmovie.android.library")
    id("popcornmovie.android.hilt")
}

android {
    namespace = "com.victor.domain.genres"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":data:genres"))
}