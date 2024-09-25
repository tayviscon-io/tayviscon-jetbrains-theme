import org.jetbrains.changelog.Changelog
import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease
import org.jetbrains.intellij.platform.gradle.tasks.VerifyPluginTask

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.10"
    id("org.jetbrains.intellij.platform") version "2.0.1"
    id("org.jetbrains.changelog") version "2.2.1"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create(properties("platformType"), properties("platformVersion"))
        pluginVerifier()
        instrumentationTools()
    }
}

changelog {
    version.set(properties("pluginVersion"))
    path.set("${project.projectDir}/CHANGELOG.md")
    groups.set(emptyList())
}

val pluginDescription = """
    <div>
      <p>Tayviscon Theme for JetBrains</p>
    </div>
""".trimIndent()

intellijPlatform {
    buildSearchableOptions = false
    pluginConfiguration {
        name = properties("pluginName")
        version = properties("pluginVersion")
        description = pluginDescription
        changeNotes = provider {
            changelog.renderItem(changelog.getLatest(), Changelog.OutputType.HTML)
        }
        ideaVersion {
            untilBuild = provider { null }
        }
    }
    publishing {
        token = System.getProperty("jetbrains.token")
    }
    pluginVerification {
        ides {
            recommended()
            select {
                types = listOf(IntelliJPlatformType.IntellijIdeaCommunity, IntelliJPlatformType.IntellijIdeaUltimate)
                channels = listOf(ProductRelease.Channel.RELEASE)
                sinceBuild = "241"
                untilBuild = "242.*"
            }
        }
        failureLevel = listOf(
            VerifyPluginTask.FailureLevel.COMPATIBILITY_PROBLEMS, VerifyPluginTask.FailureLevel.INVALID_PLUGIN
        )
    }
}