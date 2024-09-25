package io.tayviscon.theme.jetbrains.notifications

import com.intellij.ide.BrowserUtil
import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import io.tayviscon.theme.jetbrains.TayvisconMeta
import org.intellij.lang.annotations.Language

object TayvisconNotification {
    @Language("HTML")
    private val whatsNew = """
        <ul>
            <li>Fix compatibility issue</li>
        </ul>
    """.trimIndent()

    @Language("HTML")
    private val releaseNote = """
        $whatsNew
    """.trimIndent()

    @Language("HTML")
    private val welcomeMessage = """
        <p>Thank you for choosing Tayviscon.</p>
    """.trimIndent()

    private const val NOTIFICATION_GROUP_ID = "Tayviscon Theme"

    @JvmField
    val notificationIcon = IconLoader.getIcon("/icons/tayviscon-logo.svg", javaClass)

    private const val TELEGRAM_LINK = "https://t.me/tayviscon"
    private const val GITHUB_LING = "https://github.com/tayviscon-io/tayviscon-jetbrains-theme"

    fun notifyReleaseNote(project: Project) {
        val title = "Tayviscon Theme updated to v${TayvisconMeta.currentVersion}"
        val notification = NotificationGroupManager.getInstance().getNotificationGroup(NOTIFICATION_GROUP_ID)
            .createNotification(title, releaseNote, NotificationType.INFORMATION)
        addNotificationActions(notification)
        notification.icon = notificationIcon
        notification.notify(project)
    }

    fun notifyFirstlyDownloaded(project: Project) {
        val title = "Tayviscon Theme is installed"
        val notification = NotificationGroupManager.getInstance().getNotificationGroup(NOTIFICATION_GROUP_ID)
            .createNotification(title, welcomeMessage, NotificationType.INFORMATION)
        addNotificationActions(notification)
        notification.icon = notificationIcon
        notification.notify(project)
    }

    private fun addNotificationActions(notification: Notification) {
        val telegramNotificationAction = NotificationAction.createSimple("Telegram") {
            BrowserUtil.browse(TELEGRAM_LINK)
        }
        val githubNotificationAction = NotificationAction.createSimple("GitHub") {
            BrowserUtil.browse(GITHUB_LING)
        }
        notification.addAction(telegramNotificationAction)
        notification.addAction(githubNotificationAction)
    }
}