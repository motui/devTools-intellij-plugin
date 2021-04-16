package cn.motui.devtools.ui.component.action

import cn.motui.devtools.DevToolsBundle
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.popup.JBPopupFactory
import icons.Icons
import javax.swing.Icon

/**
 * 组件Action组
 */
class ComponentActionGroup constructor(
    icon: Icon,
    text: String
) : DefaultActionGroup() {

    override fun actionPerformed(e: AnActionEvent) {
        JBPopupFactory.getInstance()
            .createActionGroupPopup(
                null, this, e.dataContext, JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, true
            )
            .showUnderneathOf(e.inputEvent.component)
    }

    init {
        templatePresentation.icon = icon
        templatePresentation.text = text
        isPopup = true
    }
}

enum class ComponentGroup constructor(val text: String, val icon: Icon) {
    DEFAULT("", Icons.DevTools),
    JSON(DevToolsBundle.messagePointer("component.json.name"), Icons.Component.Json.Json)
}