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
// Other plugins


plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")

}

android {
    namespace = "com.peterchege.pinstagram"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.peterchege.pinstagram"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    packagingOptions {
        resources {
            excludes.add("/META-INF/**")
        }
    }

}
configurations {
    debugImplementation {
        exclude(group = "junit", module = "junit")
    }
}
dependencies {
    implementation(project(":core:core-datastore"))
    implementation(project(":core:core-common"))
    implementation(project(":core:core-model"))
    implementation(project(":core:core-network"))
    implementation(project(":core:core-room"))


    implementation(project(":feature:feature-auth"))
    implementation(project(":feature:feature-feed"))
    implementation(project(":feature:feature-create-post"))
    implementation(project(":feature:feature-search"))
    implementation(project(":feature:feature-notifications"))
    implementation(project(":feature:feature-profile"))
    implementation(project(":feature:feature-comments"))


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

    //lifecycle

    implementation(libs.lifecycle.runtimeKtx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.livedataKtx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.android.hilt.navigation.compose)


    //work
    implementation(libs.work.runtime)
    implementation(libs.android.hilt.ext.work)

    implementation(libs.android.dagger.hilt)
    kapt(libs.android.hilt.compiler)
    debugImplementation (libs.leakcanary.android)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.dagger.hilt.android.testing)
    androidTestImplementation(libs.android.test.compose)
    kaptAndroidTest (libs.android.hilt.compiler)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.android.test.junit4)
    androidTestImplementation(libs.android.test.espresso)

}