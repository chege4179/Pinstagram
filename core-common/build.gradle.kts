plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.peterchege.pinstagram.core.core_common"

}

dependencies {
    implementation(libs.android.coreKtx)

}