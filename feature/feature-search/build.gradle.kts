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

android {
    namespace = "com.peterchege.pinstagram.feature.feature_search"
    compileSdk = 33

    buildFeatures {
        compose = true
    }
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {

    implementation(project(":core:core-common"))
    implementation(project(":core:core-model"))
    implementation(project(":core:core-datastore"))
    implementation(project(":core:core-network"))
    implementation(project(":core:core-ui"))




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

    //lifecycle

    implementation(libs.lifecycle.runtimeKtx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.livedataKtx)

    implementation(libs.android.hilt.navigation.compose)


    //datastore
    implementation(libs.datastore)

    implementation(libs.android.dagger.hilt)
    kapt(libs.android.hilt.compiler)


    implementation(libs.retrofit.core)
}