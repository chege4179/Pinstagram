import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
}
buildscript{
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.8.0")
    }
}
android {
    namespace = "com.peterchege.pinstagram.feature.feature_create_post"
    compileSdk = 33

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {

    implementation(project(":core-common"))
    implementation(project(":core-model"))
    implementation(project(":core-room"))
    implementation(project(":core-network"))
    implementation(project(":core-datastore"))
    implementation(project(":compose-image-picker"))
    implementation(project(":core-ui"))

    implementation(libs.android.coreKtx)
    implementation(libs.android.appCompat)
    implementation(libs.android.material)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.android.test.junit4)
    androidTestImplementation(libs.android.test.espresso)

    //compose
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.compiler)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.tooling)
    implementation(libs.navigation.compose)

    implementation(libs.kotlin.serialization.json)



    // coil compose
    implementation(libs.coil.compose)

    //lifecycle
    implementation(libs.lifecycle.runtimeKtx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.livedataKtx)

    implementation(libs.android.hilt.navigation.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)

    implementation(libs.hilt.android)
    implementation(libs.hilt.ext.work)
    implementation(libs.hilt.compiler)
    kapt(libs.hilt.compiler)

    implementation(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicator)


}