plugins {
    id("conventionPluginsApp.android.core")
}

android {
    namespace = "com.soleel.ui"
}

dependencies {
    api(projects.core.common)
}