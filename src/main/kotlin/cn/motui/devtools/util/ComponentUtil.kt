package cn.motui.devtools.util

import cn.motui.devtools.ui.component.DevToolsComponent
import com.intellij.openapi.ide.CopyPasteManager
import java.awt.datatransfer.StringSelection

/**
 * 组件工具类
 */
class ComponentUtil {
    
    companion object {
        fun devToolsComponents(): List<DevToolsComponent> {
            val allSuperclasses = DevToolsComponent::class.sealedSubclasses
            return listOf()
        }

        fun copyToClipboard(message: String? = null) {
            message.let { CopyPasteManager.getInstance().setContents(StringSelection(it)) }
        }

    }
}
