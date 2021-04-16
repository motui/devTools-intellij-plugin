package cn.motui.devtools.ui

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

/**
 * 工具窗口
 */
class MainToolWindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentManager = toolWindow.contentManager
        val devToolsWindowPanel = DevToolsWindowPanel(project)
        val content = ContentFactory.SERVICE.getInstance().createContent(devToolsWindowPanel, null, false)
        contentManager.addContent(content)
        contentManager.setSelectedContent(content)
    }
}

//class GroupByActionGroup : DefaultActionGroup() {
//    override fun actionPerformed(e: AnActionEvent) {
//        JBPopupFactory.getInstance()
//            .createActionGroupPopup(null, this, e.dataContext, JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, true)
//            .showUnderneathOf(e.inputEvent.component)
//    }
//
//    init {
//        templatePresentation.icon = AllIcons.Actions.GroupBy
//        templatePresentation.setText(IdeBundle.messagePointer("group.group.by"))
//        isPopup = true
//    }
//}
