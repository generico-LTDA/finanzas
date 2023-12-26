plugins {
    id("conventionPluginsApp.android.application")
    id("conventionPluginsApp.android.app.compose")
    id("conventionPluginsApp.android.hilt")
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.stats)
    implementation(projects.feature.accounts)
    implementation(projects.feature.profile)
    implementation(projects.feature.add)
    implementation(projects.feature.createpaymentaccount)
    implementation(projects.feature.createtransaction)
    implementation(projects.feature.cancelalert)
}