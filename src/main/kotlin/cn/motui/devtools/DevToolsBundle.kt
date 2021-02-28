package cn.motui.devtools

import com.intellij.AbstractBundle
import org.jetbrains.annotations.PropertyKey

/**
 * message
 */
const val BUNDLE = "messages.DevTools"

object DevToolsBundle : AbstractBundle(BUNDLE) {

    fun messagePointer(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String {
        return DevToolsBundle.getMessage(key, *params)
    }
}
