package extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


internal fun Project.configureAndroidData(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {

        dependencies {


            add("implementation", project(":core:database"))

//            add("implementation", versionCatalog().findLibrary("hilt-navigation-compose").get())

//            add("implementation", versionCatalog().findLibrary("androidx-lifecycle-runtimeCompose").get())
//            add("implementation", versionCatalog().findLibrary("androidx-lifecycle-viewModelCompose").get())
//
//            add("implementation", versionCatalog().findLibrary("kotlinx-coroutines-android").get())
        }

    }
}