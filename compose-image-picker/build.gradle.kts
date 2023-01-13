plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}
buildscript{
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.8.0")
    }
}
android {
    namespace = "com.peterchege.compose_image_picker"
    compileSdk = 32

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.appcompat:appcompat:1.5.1")
    implementation ("androidx.compose.material3:material3:1.0.1")
    implementation ("androidx.compose.ui:ui:1.3.2")

    implementation ("com.google.accompanist:accompanist-pager:0.27.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.27.0")
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.27.0")
    implementation ("com.google.accompanist:accompanist-permissions:0.27.0")

    implementation ("androidx.navigation:navigation-compose:2.5.3")
    implementation ("io.coil-kt:coil-compose:2.2.2")
    implementation ("io.coil-kt:coil-video:2.2.2")
    implementation ("io.coil-kt:coil-gif:2.2.2")

    implementation ("com.google.android.exoplayer:exoplayer-core:2.18.2")
    implementation ("com.google.android.exoplayer:exoplayer-ui:2.18.2")

    testImplementation( "junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    implementation(libs.kotlin.serialization.json)
}