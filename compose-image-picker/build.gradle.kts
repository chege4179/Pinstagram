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

}
buildscript{

}
android {
    namespace = "com.peterchege.compose_image_picker"
    compileSdk = 33

    buildFeatures {
        compose = true
    }
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
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
    implementation(project(":core-model"))

    implementation (libs.android.coreKtx)
    implementation (libs.android.appCompat)
    implementation (libs.androidx.material3)
    implementation (libs.compose.ui)

    implementation (libs.accompanist.pager)
    implementation (libs.accompanist.pager.indicator)
    implementation (libs.accompanist.navigation.animation)
    implementation (libs.accompanist.permissions)

    implementation (libs.navigation.compose)
    implementation (libs.coil.compose)
    implementation (libs.coil.video)
    implementation (libs.coil.gif)

    implementation (libs.exoplayer.core)
    implementation (libs.exoplayer.ui)


    testImplementation( libs.test.junit4)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.android.test.espresso)

    
}