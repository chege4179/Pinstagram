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
    id("pinstagram.android.library")
    id("pinstagram.android.hilt")
}

android {
    namespace = "com.peterchege.pinstagram.core.core_network"

}

dependencies {

    implementation(project(":core:core-model"))
    implementation(project(":core:core-common"))

    implementation(libs.android.coreKtx)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.converter.moshi)


    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.serialization.json)


    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)


    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.android.test.junit4)
    androidTestImplementation(libs.io.mockk.mockk)

    androidTestImplementation(libs.okhttp.mockwebserver)
    androidTestImplementation(libs.google.truth)



}