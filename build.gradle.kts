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
    id ("com.android.application") version "7.3.1" apply false
    id ("com.android.library") version "7.3.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("com.diffplug.spotless") version "5.3.0"
    id("jacoco")


}
buildscript {
    val jacocoVersion by extra("0.2")
    dependencies {
        classpath ("com.android.tools.build:gradle:7.4.0")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.7.20")
        classpath("com.hiya:jacoco-android:$jacocoVersion")

    }
}
jacoco {
    toolVersion = "0.8.8"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
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
tasks.named("build") { finalizedBy("spotlessApply") }