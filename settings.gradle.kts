pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    includeBuild("build-logic")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MTodo"
include(":app")
include(":core:data")
include(":core:datastore")
include(":core:domain")
include(":core:network")
include(":core:model")
include(":core:designsystem")
include(":core:database")



include(":feature:mandalart")
include(":feature:daily")
include(":feature:history")
include(":feature:login")
