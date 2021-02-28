package cn.motui.devtools.util

import cn.motui.devtools.AppStorage
import com.intellij.notification.Notification
import com.intellij.notification.Notifications
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project

/**
 * ApplicationUtils
 */
inline val application: Application get() = ApplicationManager.getApplication()
inline val appStorage: AppStorage get() = AppStorage.instance

/**
 * Asserts whether the method is being called from the event dispatch thread.
 */
fun assertIsDispatchThread() = application.assertIsDispatchThread()

/**
 * Shows the notification[Notification].
 */
fun Notification.show(project: Project? = null) {
    Notifications.Bus.notify(this, project)
}

