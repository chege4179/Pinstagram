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
    id("pinstagram.android.library.compose")
}
android {
    namespace = "com.peterchege.compose_image_picker"
}

dependencies {
    implementation(project(":core:core-model"))
    implementation(libs.androidx.material3)


    implementation (libs.accompanist.navigation.animation)
    implementation (libs.accompanist.permissions)


    implementation (libs.coil.compose)
    implementation (libs.coil.video)
    implementation (libs.coil.gif)

    implementation (libs.exoplayer.core)
    implementation (libs.exoplayer.ui)

}