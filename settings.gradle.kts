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
include(":app")

include(":core:database")

include(":data:paymentaccount")
include(":data:transation")

include(":feature:home")
include(":feature:createpaymentaccount")
include(":feature:createtransaction")
