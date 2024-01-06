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

rootProject.name = "finanzas"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

include(":core:common")
include(":core:database")
include(":core:ui")
include(":core:validation")

include(":data:paymentaccount")
include(":data:transaction")

include(":feature:home")
include(":feature:accounts")
include(":feature:profile")
include(":feature:stats")
include(":feature:addmodal")
include(":feature:createpaymentaccount")
include(":feature:createtransaction")
include(":feature:cancelalert")

