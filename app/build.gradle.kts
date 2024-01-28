import com.victor.popcornmovie.PopcornMovieBuildType

plugins {
    id("popcornmovie.android.application")
//    id("popcornmovie.android.application.compose")
    id("popcornmovie.android.hilt")
}

android {
    defaultConfig {
        applicationId = "com.victor.popcornmovie"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.victor.popcornmovie.HiltTestRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        debug {
            applicationIdSuffix = PopcornMovieBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = false
            applicationIdSuffix = PopcornMovieBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    namespace = "com.victor.popcornmovie"
}

android.sourceSets.all {
    kotlin.srcDir("src/$name/kotlin")
}

dependencies {
    implementation(project(":feature:home"))
    implementation(libs.constraintLayout)
}

