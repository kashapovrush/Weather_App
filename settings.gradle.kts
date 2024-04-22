pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Weather App Test"
include(":app")
include(":core")
include(":features-mobile")
include(":features-mobile:details-screen")
include(":features-mobile:search-screen")
include(":features-mobile:favourite-screen")
include(":features-mobile:palette")
include(":features-mobile:common")
include(":core:database")
include(":core:database:database-api")
include(":core:database:database-impl")
include(":core:utils")
include(":core:network")
include(":core:network:network-api")
include(":core:network:network-impl")
