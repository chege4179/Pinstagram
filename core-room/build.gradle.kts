plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.peterchege.pinstagram.core.core_room"
    compileSdk = 33


}

dependencies {

    implementation(project(":core-model"))
    implementation(project(":core-common"))

    
    implementation(libs.android.coreKtx)
    implementation(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    implementation(libs.room.runtime)


}