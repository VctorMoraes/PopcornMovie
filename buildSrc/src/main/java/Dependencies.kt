object Versions {
    const val kotlin = "1.5.0"
    const val gradlePlugin = "4.2.1"
    const val hilt = "2.35.1"

    const val androidCore = "1.7.0"
    const val appcompat = "1.4.1"
    const val acticity = "1.4.0"
    const val material = "1.5.0"
    const val constraintLayout = "2.1.3"
    const val viewModelLifecycle = "2.4.0"

    const val rxjava = "3.0.0"

    const val glide = "4.13.0"

    const val room = "2.4.2"

    const val mockk = "1.12.0"

    const val gson = "2.8.7"
    const val retrofit = "2.9.0"
    const val okHttp = "4.9.3"

    const val junit = "4.13.2"
    const val androidXJunit = "1.1.3"
    const val espresso = "3.4.0"
}

object AndroidX {
    const val androidCore = "androidx.core:core-ktx:${Versions.androidCore}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val activity = "androidx.activity:activity-ktx:${Versions.acticity}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val viewModelLifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelLifecycle}"
}

object RxJava {
    const val rxAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxjava}"
    const val rxJava = "io.reactivex.rxjava3:rxjava:${Versions.rxjava}"
    const val rxJavaRetrofitAdapter = "com.github.akarnokd:rxjava3-retrofit-adapter:${Versions.rxjava}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
}

object Hilt {
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltTest = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomTest = "androidx.room:room-testing:${Versions.room}"
}

object Retrofit {
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

}


