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
    id ("kotlin-kapt")
    //id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.peterchege.pinstagram.feature.feature_auth"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "com.peterchege.pinstagram.feature.feature_auth.HiltTestRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }


    buildFeatures {
        compose = true
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
    implementation(project(":core:core-room"))



    implementation(libs.android.coreKtx)
    implementation(libs.android.appCompat)
    implementation(libs.android.material)

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

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)



    implementation(libs.android.dagger.hilt)
    kapt(libs.android.hilt.compiler)
    kapt(libs.android.hilt.androidx.compiler)


    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.dagger.hilt.android.testing)
    androidTestImplementation(libs.android.test.compose)
    kaptAndroidTest (libs.android.hilt.compiler)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.android.test.junit4)
    androidTestImplementation(libs.android.test.espresso)


}