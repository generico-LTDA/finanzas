plugins {
    id("conventionPluginsApp.android.feature")
}

android {
    namespace = "com.soleel.paymentaccounttype"
}
dependencies {
    implementation(project(":feature:paymentaccountcreate"))
}
