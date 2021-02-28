/**
 * Constants
 */
@file:Suppress("SpellCheckingInspection")

package cn.motui.devtools

import java.nio.file.Path
import java.nio.file.Paths

private val USER_HOME_PATH = System.getProperty("user.home")
val TRANSLATION_DIRECTORY: Path = Paths.get(USER_HOME_PATH, ".devTools")

const val STORAGE_NAME = "it.motui.devTools.xml"

const val NOTIFICATIONS_ID: String = "DevTools"