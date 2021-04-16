package cn.motui.devtools.ui.component.json

import com.intellij.codeInsight.actions.FileInEditorProcessor
import com.intellij.codeInsight.actions.LastRunReformatCodeOptionsProvider
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.command.CommandEvent
import com.intellij.openapi.command.CommandListener
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.codeStyle.CodeStyleManager
import icons.Icons
import java.awt.event.ActionEvent

/**
 * 转换组件
 */
abstract class TransformComponent constructor(private val project: Project) : TransformForm() {

    init {
        toLeftButton.icon = Icons.Common.Left
        toRightButton.icon = Icons.Common.Right
        toLeftButton.addActionListener {
            toLeftActionListener(
                project,
                leftTextField as MyLanguageTextField,
                rightTextField as MyLanguageTextField,
                it
            )
        }
        toRightButton.addActionListener {
            toRightActionListener(
                project,
                leftTextField as MyLanguageTextField,
                rightTextField as MyLanguageTextField,
                it
            )
        }
    }

    /**
     * left 转成 right
     */
    abstract fun toRight(
        project: Project,
        leftTextField: MyLanguageTextField,
        rightTextField: MyLanguageTextField,
        actionEvent: ActionEvent
    )

    /**
     * right 转成 left
     */
    abstract fun toLeft(
        project: Project,
        leftTextField: MyLanguageTextField,
        rightTextField: MyLanguageTextField,
        actionEvent: ActionEvent
    )

    /**
     * to left 动作监听
     */
    fun toLeftActionListener(
        project: Project,
        leftTextField: MyLanguageTextField,
        rightTextField: MyLanguageTextField,
        actionEvent: ActionEvent
    ) {
        toLeft(project, leftTextField, rightTextField, actionEvent)
        project.messageBus.connect().subscribe(CommandListener.TOPIC, object : CommandListener {
            var insideCommand = true
            override fun commandFinished(event: CommandEvent) {
                if (insideCommand) {
                    insideCommand = false
                    format(project, leftTextField)
                }
            }
        })
    }

    /**
     * to right 动作监听
     */
    fun toRightActionListener(
        project: Project,
        leftTextField: MyLanguageTextField,
        rightTextField: MyLanguageTextField,
        actionEvent: ActionEvent
    ) {
        toRight(project, leftTextField, rightTextField, actionEvent)
        project.messageBus.connect().subscribe(CommandListener.TOPIC, object : CommandListener {
            var insideCommand = true
            override fun commandFinished(event: CommandEvent) {
                if (insideCommand) {
                    insideCommand = false
                    format(project, rightTextField)
                }
            }
        })
    }

    /**
     * 格式化
     */
    private fun format(project: Project, languageTextField: MyLanguageTextField) {
        languageTextField.editor?.let { editor ->
            PsiDocumentManager.getInstance(project).getPsiFile(editor.document)?.let { file ->
                val provider = LastRunReformatCodeOptionsProvider(PropertiesComponent.getInstance())
                val currentRunOptions = provider.getLastRunOptions(file)
                FileInEditorProcessor(file, editor, currentRunOptions).processCode()
            }
        }
    }
}
