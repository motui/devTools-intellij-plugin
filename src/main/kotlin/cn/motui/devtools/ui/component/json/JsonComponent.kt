package cn.motui.devtools.ui.component.json

import cn.motui.devtools.DevToolsBundle.messagePointer
import cn.motui.devtools.ui.component.DevToolsComponent
import cn.motui.devtools.ui.component.action.ComponentGroup
import cn.motui.devtools.util.application
import com.intellij.codeInsight.actions.FileInEditorProcessor
import com.intellij.codeInsight.actions.LastRunReformatCodeOptionsProvider
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import icons.Icons
import org.apache.commons.lang.StringEscapeUtils
import org.apache.commons.lang3.StringUtils
import java.awt.event.ActionEvent
import java.util.regex.Pattern
import javax.swing.Icon
import javax.swing.JComponent

/**
 * JSON组件
 */
class JsonComponent constructor(
    project: Project
) : JsonForm(project), DevToolsComponent {
    init {
        formatButton.text = messagePointer("component.json.base.button.format")
        compressButton.text = messagePointer("component.json.base.button.compress")
        compressButton.toolTipText = messagePointer("component.json.base.button.compress.description")
        escapeButton.text = messagePointer("component.json.base.button.escape")
        escapeButton.toolTipText = messagePointer("component.json.base.button.escape.description")
        unescapedButton.text = messagePointer("component.json.base.button.unescaped")
        unicodeToChineseButton.text = messagePointer("component.json.base.button.unicode_chinese")
        chineseToUnicodeButton.text = messagePointer("component.json.base.button.chinese_unicode")
        // action
        formatButton.addActionListener { formatAction(project, it) }
        compressButton.addActionListener { compressAction(project, it) }
        escapeButton.addActionListener { escapeAction(project, it) }
        unescapedButton.addActionListener { unescapedAction(project, it) }
        unicodeToChineseButton.addActionListener { unicodeToChineseAction(project, it) }
        chineseToUnicodeButton.addActionListener { chineseToUnicodeAction(project, it) }
    }

    override fun componentId(): String {
        return ""
    }

    override fun name(): String {
        return messagePointer("component.json.base.name")
    }

    override fun description(): String {
        return messagePointer("component.json.base.description")
    }

    override fun icon(): Icon {
        return Icons.Component.Json.BaseJson
    }

    override fun componentGroup(): ComponentGroup {
        return ComponentGroup.JSON
    }

    override fun content(): JComponent {
        return root()
    }

    /**
     * 格式化
     */
    private fun formatAction(project: Project, actionEvent: ActionEvent) {
        languageTextField.editor?.let { editor ->
            PsiDocumentManager.getInstance(project).getPsiFile(editor.document)?.let { file ->
                val provider = LastRunReformatCodeOptionsProvider(PropertiesComponent.getInstance())
                val currentRunOptions = provider.getLastRunOptions(file)
                FileInEditorProcessor(file, editor, currentRunOptions).processCode()
            }
        }
    }

    /**
     * 压缩
     */
    private fun compressAction(project: Project, actionEvent: ActionEvent) {
        application.invokeLater {
            languageTextField.document
            val text = languageTextField.text
            languageTextField.text = StringUtils.replaceEach(
                text,
                arrayOf("\n", "\r", "\r\n", " "),
                arrayOf("", "", "", ""),
            )
        }
    }

    /**
     * 转义
     */
    private fun escapeAction(project: Project, actionEvent: ActionEvent) {
        application.invokeLater {
            val text = languageTextField.text
            languageTextField.text = text.replace("\"", "\\\"")
        }
    }

    /**
     * 去除转义
     */
    private fun unescapedAction(project: Project, actionEvent: ActionEvent) {
        application.invokeLater {
            val text = languageTextField.text
            languageTextField.text = text.replace("\\\"", "\"")
        }
    }

    /**
     * Unicode 转 中文
     */
    private fun unicodeToChineseAction(project: Project, actionEvent: ActionEvent) {
        application.invokeLater {
            val text = languageTextField.text
            languageTextField.text = StringEscapeUtils.unescapeJava(text)
        }
    }

    /**
     * 中文 转 Unicode
     */
    private fun chineseToUnicodeAction(project: Project, actionEvent: ActionEvent) {
        application.invokeLater {
            val text = languageTextField.text
            val matcher = chinesePattern.matcher(text)
            var result = text
            while (matcher.find()) {
                val group = matcher.group(0)
                result = result.replace(group, StringEscapeUtils.escapeJava(group))
            }
            languageTextField.text = result
        }
    }

    companion object {
        val chinesePattern: Pattern = Pattern.compile("([\\u4e00-\\u9fa5]+)")
    }
}
