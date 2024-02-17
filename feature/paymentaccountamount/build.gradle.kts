plugins {
    id("conventionPluginsApp.android.feature")
}

android {
    namespace = "com.soleel.paymentaccountamount"
}
dependencies {
    implementation(project(":feature:paymentaccountcreate"))
    implementation(project(":feature:paymentaccountcreate"))
}
