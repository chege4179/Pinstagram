package com.peterchege.pinstagram.build_logic.convention.extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
        }

        dependencies {

            add("implementation", libs.findLibrary("android.appCompat").get())
            add("implementation", libs.findLibrary("android.coreKtx").get())
            add("implementation", libs.findLibrary("android.material").get())
            add("implementation", libs.findLibrary("coil.compose").get())
            add("implementation", libs.findLibrary("accompanist.pager").get())
            add("implementation", libs.findLibrary("accompanist.pager.indicator").get())
            add("implementation", libs.findLibrary("compose.activity").get())
            add("implementation", libs.findLibrary("compose.material").get())
            add("implementation", libs.findLibrary("compose.materialIcons").get())
            add("implementation", libs.findLibrary("compose.ui").get())
            add("implementation", libs.findLibrary("compose.ui.tooling").get())
            add("implementation", libs.findLibrary("compose.ui.tooling.preview").get())
            add("implementation", libs.findLibrary("androidx.compose.foundation").get())
            add("implementation", libs.findLibrary("androidx.compose.foundation.layout").get())
            add("implementation", libs.findLibrary("lottie.compose").get())
            add("implementation", libs.findLibrary("lifecycle.livedataKtx").get())
            add("implementation", libs.findLibrary("lifecycle.runtimeKtx").get())
            add("implementation", libs.findLibrary("lifecycle.viewmodel.compose").get())
            add("implementation", libs.findLibrary("lifecycle.runtime.compose").get())
            add("implementation", libs.findLibrary("navigation.compose").get())
        }

        testOptions {
            unitTests {
                // For Robolectric
                isIncludeAndroidResources = true
            }
        }
    }
}

