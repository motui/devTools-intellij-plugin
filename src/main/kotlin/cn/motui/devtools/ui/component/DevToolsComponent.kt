package cn.motui.devtools.ui.component

import cn.motui.devtools.ui.component.action.ComponentGroup
import javax.swing.Icon
import javax.swing.JComponent

/**
 * 开发组件
 */
interface DevToolsComponent {

    /**
     * 组件ID，需要全局唯一，且是固定值
     */
    fun componentId(): String

    /**
     * 组件名称
     */
    fun name(): String

    /**
     * 组件描述
     */
    fun description(): String

    /**
     * 组件图标 13X13
     */
    fun icon(): Icon

    /**
     * 组件组
     */
    fun componentGroup(): ComponentGroup

    /**
     * 组件面板
     */
    fun content(): JComponent
}