package icons

import com.intellij.ui.IconManager
import javax.swing.Icon

object Icons {
    @JvmField
    val DevTools: Icon = load("/icons/devTools.svg")

    object Component {
        @JvmField
        val Time: Icon = load("/icons/component/time.svg")
    }

    object Common {
        @JvmField
        val CopyToClipboard: Icon = load("/icons/common/copyToClipboard.svg")
    }

    private fun load(path: String): Icon {
        return IconManager.getInstance().getIcon(path, Icons::class.java)
    }
}

