import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.victor.popcornmovie.PopcornMovieBuildType
import com.victor.popcornmovie.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = "com.victor.popcornmovie"
                    versionCode = 1
                    versionName = "1.0"

                    testInstrumentationRunner = "com.victor.popcornmovie.HiltTestRunner"
                }

                buildTypes {
                    debug {
                        applicationIdSuffix = PopcornMovieBuildType.DEBUG.applicationIdSuffix
                    }
                    val release  = getByName("release") {
                        isMinifyEnabled = false
                        applicationIdSuffix = PopcornMovieBuildType.RELEASE.applicationIdSuffix
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
                        )
                    }
                }

                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
            }
        }
    }

}