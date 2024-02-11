plugins {
    id("popcornmovie.android.application")
    id("popcornmovie.android.hilt")
}

android {
    namespace = "com.victor.popcornmovie"
}

dependencies {
    implementation(project(":feature:home"))
}



