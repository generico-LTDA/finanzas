plugins {
    id("conventionPluginsApp.android.feature")
}

android {
    namespace = "com.soleel.paymentaccountname"
}
dependencies {
    implementation(project(":feature:paymentaccountcreate"))
}
