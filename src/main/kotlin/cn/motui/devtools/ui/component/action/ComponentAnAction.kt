package cn.motui.devtools.ui.component.action

import cn.motui.devtools.ui.DevToolsWindowPanel
import cn.motui.devtools.ui.component.DevToolsComponent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 组件AnAction
 */
class ComponentAnAction constructor(
    private val devToolsWindowPanel: DevToolsWindowPanel,
    private val devToolsComponent: DevToolsComponent
) : AnAction(devToolsComponent.name(), devToolsComponent.description(), devToolsComponent.icon()) {
    override fun actionPerformed(e: AnActionEvent) {
        devToolsWindowPanel.setContent(devToolsComponent.content())
    }
}