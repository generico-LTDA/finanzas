package plugins

import com.android.build.gradle.LibraryExtension
import extensions.configureAndroidData
import extensions.configureAndroidFeature
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType


class AndroidAppDataConvetionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.library")
//                apply("dagger.hilt.android.plugin")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidData(extension)
        }
    }
}