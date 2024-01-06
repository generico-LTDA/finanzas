plugins {
    id("conventionPluginsApp.android.core")
}

android {
    namespace = "com.soleel.validation"
}

dependencies {
    implementation(projects.core.ui)
}