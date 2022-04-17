plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}
android {
    compileSdkVersion(32)

    defaultConfig {
        applicationId = "com.victor.popcornmovie"
        minSdkVersion(27)
        targetSdkVersion(32)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.victor.popcornmovie.HiltTestRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

    implementation(AndroidX.androidCore)
    implementation(AndroidX.appcompat)
    implementation(AndroidX.activity)

    implementation(AndroidX.material)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.viewModelLifecycle)

    implementation(Glide.glide)
    annotationProcessor(Glide.glide)

    implementation(Room.runtime)
    kapt(Room.compiler)

    implementation(Retrofit.gson)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitGson)
    implementation(Retrofit.okHttp)

    androidTestImplementation(Hilt.hiltTest)
    kaptAndroidTest(Hilt.hiltTest)

    testImplementation("junit:junit:${Versions.junit}")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    androidTestImplementation("io.mockk:mockk:1.12.3")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.test.ext:junit:${Versions.androidXJunit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espresso}")
}

kapt {
    correctErrorTypes = true
}