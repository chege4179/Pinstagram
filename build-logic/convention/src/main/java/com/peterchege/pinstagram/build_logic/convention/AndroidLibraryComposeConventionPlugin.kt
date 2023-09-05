
import com.android.build.gradle.LibraryExtension
import com.peterchege.pinstagram.build_logic.convention.extensions.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")


            val extension = extensions.getByType<LibraryExtension>()
            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = 34
            }
            configureAndroidCompose(extension)
        }
    }

}