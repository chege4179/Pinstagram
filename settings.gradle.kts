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
pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Pinstagram"
include (":app")
include(":core:core-common")
include(":core:core-model")
include(":feature:feature-auth")
include(":core:core-network")
include(":core:core-datastore")
include(":feature:feature-feed")
include(":feature:feature-create-post")
include(":feature:feature-search")
include(":feature:feature-notifications")
include(":feature:feature-profile")
include(":compose-image-picker")
include(":core:core-room")
include(":core:core-ui")
include(":core:core-work")
include(":feature:feature-comments")

