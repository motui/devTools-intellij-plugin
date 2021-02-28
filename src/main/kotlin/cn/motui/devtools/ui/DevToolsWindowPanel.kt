package cn.motui.devtools.ui

import cn.motui.devtools.DevToolsBundle.messagePointer
import cn.motui.devtools.ui.component.TimeComponent
import cn.motui.devtools.ui.component.action.ComponentAnAction
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.SimpleToolWindowPanel

/**
 * devTools 窗口
 */
class DevToolsWindowPanel : SimpleToolWindowPanel(true, true) {
    private val componentToolBar = ComponentToolBar()

    init {
        // 扫描所有的组件，添加到ToolBar
        val timeComponent = TimeComponent()
        componentToolBar.add(ComponentAnAction(this, timeComponent))

        toolbar = componentToolBar.toActionToolBar().component
        // 获取主面板
        setContent(MainForm().root())
    }
}

class ComponentToolBar {
    private val defaultActionGroup = DefaultActionGroup()
    private val favoriteActionGroup = DefaultActionGroup()

    fun addFavorite(componentAnAction: ComponentAnAction) {
        favoriteActionGroup.add(componentAnAction)
    }

    fun add(componentAnAction: ComponentAnAction) {
        defaultActionGroup.add(componentAnAction)
    }

    fun add(componentAnAction: ComponentAnAction, favorite: Boolean) {
        defaultActionGroup.add(componentAnAction)
        if (favorite) {
            addFavorite(componentAnAction)
        }
    }

    fun toActionToolBar(): ActionToolbar {
        val group = DefaultActionGroup()
        if (favoriteActionGroup.getChildren(null).isNotEmpty()) {
            group.addAll(favoriteActionGroup)
            group.addSeparator()
        }
        group.addAll(defaultActionGroup)
        return ActionManager.getInstance().createActionToolbar(messagePointer("plugin.name"), group, true)
    }
}