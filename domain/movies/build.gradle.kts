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
    implementation("androidx.paging:paging-runtime-ktx:3.2.0")
}