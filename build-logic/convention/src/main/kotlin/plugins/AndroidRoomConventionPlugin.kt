package plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import extensions.configureAndroidRoom
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType


class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("com.google.devtools.ksp")
//                apply("dagger.hilt.android.plugin")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidRoom(extension)
        }
    }
}
