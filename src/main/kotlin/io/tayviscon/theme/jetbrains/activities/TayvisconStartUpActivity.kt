package io.tayviscon.theme.jetbrains.activities

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import io.tayviscon.theme.jetbrains.TayvisconMeta
import io.tayviscon.theme.jetbrains.notifications.TayvisconNotification
import io.tayviscon.theme.jetbrains.settings.TayvisconSettings

class TayvisconStartUpActivity : ProjectActivity, DumbAware {
    override suspend fun execute(project: Project) {
        val settings = TayvisconSettings.instance
        if (settings.version.isEmpty()) {
            settings.version = TayvisconMeta.currentVersion
            TayvisconNotification.notifyFirstlyDownloaded(project)
            return
        }
        if (TayvisconMeta.currentVersion != settings.version) {
            settings.version = TayvisconMeta.currentVersion
            TayvisconNotification.notifyReleaseNote(project)
        }
    }
}