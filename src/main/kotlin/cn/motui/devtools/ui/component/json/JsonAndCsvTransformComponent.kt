package cn.motui.devtools.ui.component.json

import cn.motui.devtools.DevToolsBundle
import cn.motui.devtools.ui.component.DevToolsComponent
import cn.motui.devtools.ui.component.action.ComponentGroup
import cn.motui.devtools.util.JsonUtil
import com.intellij.json.JsonLanguage
import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.openapi.project.Project
import com.intellij.ui.LanguageTextField
import icons.Icons
import java.awt.event.ActionEvent
import javax.swing.Icon
import javax.swing.JComponent

/**
 * Json 和 CSV 互转Form
 */
class JsonAndCsvTransformComponent constructor(
    private val project: Project
) : TransformComponent(project), DevToolsComponent {

    init {
        toLeftButton.text = DevToolsBundle.messagePointer("component.json.transform.csv.button.to_json")
        toRightButton.text = DevToolsBundle.messagePointer("component.json.transform.csv.button.to_csv")
    }

    override fun createUIComponents() {
        leftTextField = MyLanguageTextField(
            JsonLanguage.INSTANCE,
            project, "{\n    \n}", LanguageTextField.SimpleDocumentCreator()
        )
        rightTextField = MyLanguageTextField(
            PlainTextLanguage.INSTANCE,
            project, "", LanguageTextField.SimpleDocumentCreator()
        )
    }

    override fun componentId(): String {
        return ""
    }

    override fun name(): String {
        return DevToolsBundle.messagePointer("component.json.transform.csv.name")
    }

    override fun description(): String {
        return DevToolsBundle.messagePointer("component.json.transform.csv.description")
    }

    override fun icon(): Icon {
        return Icons.Component.Json.Csv
    }

    override fun componentGroup(): ComponentGroup {
        return ComponentGroup.JSON
    }

    override fun content(): JComponent {
        return root()
    }

    override fun toRight(
        project: Project,
        leftTextField: MyLanguageTextField,
        rightTextField: MyLanguageTextField,
        actionEvent: ActionEvent
    ) {
        rightTextField.text = JsonUtil.jsonToCsv(leftTextField.text)
    }

    override fun toLeft(
        project: Project,
        leftTextField: MyLanguageTextField,
        rightTextField: MyLanguageTextField,
        actionEvent: ActionEvent
    ) {
        leftTextField.text = JsonUtil.csvToJson(rightTextField.text)
    }
}