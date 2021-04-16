package cn.motui.devtools.util

import cn.motui.devtools.ui.component.DevToolsComponent
import java.util.*

/**
 * 组件工具
 */
object ComponentUtil {
    /**
     * 扫描组件
     */
    fun scan(): List<DevToolsComponent> {
        val serviceLoader = ServiceLoader.load(DevToolsComponent::class.java)
        val components = mutableListOf<DevToolsComponent>()
        serviceLoader.forEach {
            components.add(it)
        }
        return components
    }
}