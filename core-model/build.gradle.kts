plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")


}

//apply (plugin = "kotlinx-serialization")
buildscript{
    dependencies {

    }
}
android {
    namespace = "com.peterchege.pinstagram.core.core_model"
    compileSdk = 33

}

dependencies {

    implementation(libs.android.coreKtx)
    implementation(libs.kotlin.serialization.json)

}