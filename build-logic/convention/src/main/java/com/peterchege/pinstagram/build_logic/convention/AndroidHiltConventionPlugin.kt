
import com.android.build.api.dsl.ApplicationExtension
import com.google.devtools.ksp.gradle.KspExtension
import com.peterchege.pinstagram.build_logic.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")
            pluginManager.apply("dagger.hilt.android.plugin")

//            extensions.configure<KspExtension>{
//                arg("correctErrorTypes", true.toString())
//            }

            dependencies {
                add("implementation", (libs.findLibrary("android.dagger.hilt").get()))
                add("ksp",(libs.findLibrary("android.hilt.compiler").get()))
                add("implementation",(libs.findLibrary("android.hilt.ext.work").get()))
                add("implementation",(libs.findLibrary("android.hilt.androidx.compiler").get()))
                add("implementation",(libs.findLibrary("android.hilt.navigation.compose").get()))

            }

        }
    }

}