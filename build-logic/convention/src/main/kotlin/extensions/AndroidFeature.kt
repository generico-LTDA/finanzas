package extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidFeature(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {

        dependencies {
            add("implementation", project(":data:paymentaccount"))
            add("implementation", project(":data:transaction"))

            add("implementation", project(":core:ui"))
            add("implementation", project(":core:common"))

            add("implementation", versionCatalog().findLibrary("hilt-navigation-compose").get())

            add("implementation", versionCatalog().findLibrary("androidx-lifecycle-runtimeCompose").get())
            add("implementation", versionCatalog().findLibrary("androidx-lifecycle-viewModelCompose").get())

            add("implementation", versionCatalog().findLibrary("kotlinx-coroutines-android").get())
        }

    }
}