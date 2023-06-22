

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidCoreModulesConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            dependencies {
                add("implementation", project(":core:core-ui"))
                add("implementation", project(":core:core-network"))
                add("implementation", project(":core:core-common"))
                add("implementation", project(":core:core-datastore"))
                add("implementation", project(":core:core-model"))
                add("implementation", project(":core:core-work"))
                add("implementation", project(":core:core-room"))

            }
        }
    }
}
