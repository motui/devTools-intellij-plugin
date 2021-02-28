package cn.motui.devtools.ui.component.action

import cn.motui.devtools.DevToolsBundle
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.popup.JBPopupFactory
import javax.swing.Icon

/**
 * 组件Action组
 */
class ComponentActionGroup constructor(
    private val icon: Icon,
    private val messagePointer: String
) : DefaultActionGroup() {

    override fun actionPerformed(e: AnActionEvent) {
        JBPopupFactory.getInstance()
            .createActionGroupPopup(null, this, e.dataContext, JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, true)
            .showUnderneathOf(e.inputEvent.component)
    }

    init {
        templatePresentation.icon = icon
        templatePresentation.setText(DevToolsBundle.messagePointer(messagePointer))
        isPopup = true
    }
}

enum class ComponentGroup constructor(val text: String?, val icon: Icon?) {
    DEFAULT(null, null)
}