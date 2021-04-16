package icons

import com.intellij.icons.AllIcons
import com.intellij.ui.IconManager
import javax.swing.Icon

object Icons {
    @JvmField
    val DevTools: Icon = load("/icons/devTools.svg")

    object Component {
        @JvmField
        val Time: Icon = load("/icons/component/time.svg")

        object Json {
            @JvmField
            val Json: Icon = load("/icons/component/json.svg")

            @JvmField
            val BaseJson: Icon = AllIcons.FileTypes.Json

            @JvmField
            val Xml: Icon = AllIcons.FileTypes.Xml

            @JvmField
            val Yaml: Icon = AllIcons.FileTypes.Yaml

            @JvmField
            val Csv: Icon = load("/icons/component/csv.svg")
        }
    }

    object Common {
        @JvmField
        val CopyToClipboard: Icon = load("/icons/common/copyToClipboard.svg")

        @JvmField
        val Left: Icon = AllIcons.Actions.Back

        @JvmField
        val Right: Icon = AllIcons.Actions.Forward

    }

    private fun load(path: String): Icon {
        return IconManager.getInstance().getIcon(path, Icons::class.java)
    }
}

