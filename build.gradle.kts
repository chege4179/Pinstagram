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

//    id ("com.android.application") version "7.3.1" apply false
//    id ("com.android.library") version "7.3.1" apply false
//    id ("org.jetbrains.kotlin.android") version "1.9.10" apply false
//    id("org.jetbrains.kotlin.jvm") version "1.9.10" apply false
plugins {

    id("com.diffplug.spotless") version "5.3.0"

    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.application) apply false

}
buildscript {

    dependencies {
        classpath (libs.gradle)
        classpath (libs.kotlin.gradle.plugin)
        classpath (libs.hilt.android.gradle.plugin)
        classpath (libs.kotlin.serialization)
        classpath (libs.hilt.android.gradle.plugin)
    }
}







apply(plugin = "com.diffplug.spotless")
spotless {
    kotlin {
        target("**/*.kt")
        licenseHeaderFile(
            rootProject.file("${project.rootDir}/spotless/LICENSE.txt"),
            "^(package|object|import|interface)"
        )
    }
    format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
        // Look for the first line that doesn't have a block comment (assumed to be the license)
        licenseHeaderFile(rootProject.file("spotless/LICENSE.txt"), "(^(?![\\/ ]\\*).*$)")
    }
}