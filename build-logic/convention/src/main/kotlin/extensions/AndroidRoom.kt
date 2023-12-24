package extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidRoom(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            add("implementation", versionCatalog().findLibrary("room-runtime").get())
            add("implementation", versionCatalog().findLibrary("room-ktx").get())
            add("ksp", versionCatalog().findLibrary("room-compiler").get())
        }
    }
}