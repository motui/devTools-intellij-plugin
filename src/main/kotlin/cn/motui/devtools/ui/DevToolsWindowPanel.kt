package cn.motui.devtools.ui

import cn.motui.devtools.DevToolsBundle.messagePointer
import cn.motui.devtools.ui.component.DevToolsComponent
import cn.motui.devtools.ui.component.json.JsonComponent
import cn.motui.devtools.ui.component.TimeComponent
import cn.motui.devtools.ui.component.action.ComponentActionGroup
import cn.motui.devtools.ui.component.action.ComponentAnAction
import cn.motui.devtools.ui.component.action.ComponentGroup
import cn.motui.devtools.ui.component.json.JsonAndCsvTransformComponent
import cn.motui.devtools.ui.component.json.JsonAndXmlTransformComponent
import cn.motui.devtools.ui.component.json.JsonAndYamlTransformComponent
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import java.util.stream.Collectors

/**
 * devTools 窗口
 */
class DevToolsWindowPanel constructor(
    project: Project
) : SimpleToolWindowPanel(true, true) {
    private val componentToolBar = ComponentToolBar()

    init {
        // 扫描所有的组件，添加到ToolBar
        val components = mutableListOf<DevToolsComponent>()

        components.add(TimeComponent())
        components.add(JsonComponent(project))
        components.add(JsonAndXmlTransformComponent(project))
        components.add(JsonAndCsvTransformComponent(project))
        components.add(JsonAndYamlTransformComponent(project))

        val map = components.stream().collect(Collectors.groupingBy(DevToolsComponent::componentGroup))
        val defaultComponents = map.remove(ComponentGroup.DEFAULT)
        defaultComponents?.forEach { devToolsComponent ->
            componentToolBar.add(ComponentAnAction(this, devToolsComponent))
        }
        map.forEach { (componentGroup, components) ->
            val actionGroup = ComponentActionGroup(componentGroup.icon, componentGroup.text)
            components.forEach { devToolsComponent ->
                actionGroup.add(ComponentAnAction(this, devToolsComponent))
            }
            componentToolBar.add(actionGroup)
        }
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

    fun add(componentActionGroup: ComponentActionGroup) {
        defaultActionGroup.add(componentActionGroup)
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