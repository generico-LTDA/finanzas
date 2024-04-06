plugins {
    id("conventionPluginsApp.android.core")
}

android {
    namespace = "com.soleel.database"
}

dependencies {
    api(projects.core.common)
}