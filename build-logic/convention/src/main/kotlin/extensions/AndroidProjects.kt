package extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidProjects(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            add("implementation", project(":feature:home"))
            add("implementation", project(":feature:createpaymentaccount"))
            add("implementation", project(":feature:createtransaction"))
        }
    }
}