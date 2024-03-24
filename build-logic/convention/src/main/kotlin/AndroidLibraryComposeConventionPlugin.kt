import com.android.build.gradle.LibraryExtension
import com.victor.popcornmovie.configureAndroidCompose
import com.victor.popcornmovie.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            dependencies {
                add("implementation", libs.findLibrary("coil").get())
                add("implementation", libs.findLibrary("coil-compose").get())
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}