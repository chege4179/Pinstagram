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
    id("pinstagram.android.application")
    id("pinstagram.android.compose")
    id("pinstagram.android.hilt")
    id("pinstagram.android.core.modules")
}

android {
    namespace = "com.peterchege.pinstagram"


    defaultConfig {
        applicationId = "com.peterchege.pinstagram"
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
    packaging {
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

    implementation(project(":feature:feature-auth"))
    implementation(project(":feature:feature-feed"))
    implementation(project(":feature:feature-create-post"))
    implementation(project(":feature:feature-search"))
    implementation(project(":feature:feature-notifications"))
    implementation(project(":feature:feature-profile"))
    implementation(project(":feature:feature-comments"))





    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.android.test.junit4)
    androidTestImplementation(libs.android.test.espresso)


    ksp(libs.android.hilt.compiler)
    //work
    implementation(libs.work.runtime)

    debugImplementation (libs.leakcanary.android)
    debugImplementation(libs.compose.ui.test.manifest)

    androidTestImplementation(libs.android.test.compose)

    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.android.test.junit4)
    androidTestImplementation(libs.android.test.espresso)

}