package plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import extensions.configureAndroidRoom
import extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType


class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("implementation", versionCatalog().findLibrary("room-runtime").get())
                add("implementation", versionCatalog().findLibrary("room-ktx").get())
                add("ksp", versionCatalog().findLibrary("room-compiler").get())
            }
        }
    }
}
