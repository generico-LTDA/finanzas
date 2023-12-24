plugins {
    id("conventionPluginsApp.android.application")
    id("conventionPluginsApp.android.app.compose")
    id("conventionPluginsApp.android.hilt")
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.createpaymentaccount)
    implementation(projects.feature.createtransaction)
}