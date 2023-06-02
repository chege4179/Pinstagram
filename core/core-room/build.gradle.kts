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

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.peterchege.pinstagram.core.core_room"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {

    implementation(project(":core:core-model"))
    implementation(project(":core:core-common"))



    implementation(libs.android.coreKtx)


    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    api(libs.room.runtime)

    implementation(libs.android.dagger.hilt)

    kapt(libs.android.hilt.compiler)

    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.dagger.hilt.android.testing)
    androidTestImplementation(libs.android.test.compose)
    kaptAndroidTest (libs.android.hilt.compiler)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.android.test.junit4)
    androidTestImplementation(libs.android.test.espresso)

}