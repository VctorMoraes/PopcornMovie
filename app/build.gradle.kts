import com.victor.popcornmovie.PopcornMovieBuildType

plugins {
    id("popcornmovie.android.application")
    id("popcornmovie.android.application.compose")
    id("popcornmovie.android.hilt")
    id("org.jetbrains.kotlin.android")
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

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":domain:movies"))
    implementation(project(":domain:genres"))
    implementation("androidx.paging:paging-runtime-ktx:3.2.0")


    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtimeCompose)


    implementation(libs.androidx.material2)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.constraintLayout)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    implementation(libs.glide)


    implementation(libs.retrofit.core)
    implementation(libs.okhttp.logging)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
    androidTestImplementation("io.mockk:mockk:1.13.5")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

