pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Popcorn Movie"
include(":app")
include(":core:common")
include(":core:database")
include(":core:model")
include(":core:network")
include(":domain:genres")
include(":domain:movies")
include(":data:genres")
include(":data:movies")
