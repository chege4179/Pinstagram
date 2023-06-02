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
    namespace = "com.peterchege.pinstagram.core.core_work"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }


}

dependencies {
    implementation(project(":core:core-network"))
    implementation(project(":core:core-model"))
    implementation(project(":core:core-room"))
    implementation(project(":core:core-network"))
    implementation(project(":core:core-common"))


    implementation(libs.work.runtime)

    implementation(libs.android.coreKtx)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.android.dagger.hilt)
    implementation(libs.android.hilt.ext.work)


    kapt(libs.android.hilt.compiler)

}