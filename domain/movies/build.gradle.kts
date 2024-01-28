plugins {
    id("popcornmovie.android.library")
    id("popcornmovie.android.hilt")
}

android {
    namespace = "com.victor.domain.movies"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":data:movies"))
    implementation(libs.androidx.paging.common.ktx)
}