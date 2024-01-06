import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}





java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApp") {
            id = "conventionPluginsApp.android.application"
            implementationClass = "plugins.AndroidAppConventionPlugin"
        }

        register("androidLib") {
            id = "conventionPluginsApp.android.lib"
            implementationClass = "plugins.AndroidLibConventionPlugin"
        }

        register("androidAppCompose") {
            id = "conventionPluginsApp.android.app.compose"
            implementationClass = "plugins.AndroidAppComposeConventionPlugin"
        }

        register("androidLibCompose") {
            id = "conventionPluginsApp.android.lib.compose"
            implementationClass = "plugins.AndroidLibComposeConventionPlugin"
        }

        register("androidHilt") {
            id = "conventionPluginsApp.android.hilt"
            implementationClass = "plugins.AndroidHiltConventionPlugin"
        }

        register("androidRoom") {
            id = "conventionPluginsApp.android.room"
            implementationClass = "plugins.AndroidRoomConventionPlugin"
        }

        register("androidFeature") {
            id = "conventionPluginsApp.android.feature"
            implementationClass = "plugins.AndroidAppFeatureConventionPlugin"
        }

        register("androidData") {
            id = "conventionPluginsApp.android.data"
            implementationClass = "plugins.AndroidAppDataConvetionPlugin"
        }

        register("androidCore") {
            id = "conventionPluginsApp.android.core"
            implementationClass = "plugins.AndroidAppCoreConventionPlugin"
        }

    }
}