/*
 * Copyright 2023 PInstagram
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")

}
buildscript{
    
}
android {
    namespace = "com.peterchege.pinstagram.feature.feature_create_post"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }

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

    implementation(libs.kotlin.collections.immutable)



    // coil compose
    implementation(libs.coil.compose)

    //lifecycle
    implementation(libs.lifecycle.runtimeKtx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.livedataKtx)

    implementation(libs.android.hilt.navigation.compose)


    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)



    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)

    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicator)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)


}