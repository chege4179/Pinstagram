plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.peterchege.pinstagram.core.core_datastore"

}

dependencies {

    implementation(libs.android.coreKtx)
    implementation(libs.datastore)
    implementation(libs.kotlin.serialization.json)
}