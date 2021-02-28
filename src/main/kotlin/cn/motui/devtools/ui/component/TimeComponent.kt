package cn.motui.devtools.ui.component

import cn.motui.devtools.DevToolsBundle.messagePointer
import cn.motui.devtools.NOTIFICATIONS_ID
import cn.motui.devtools.ui.component.action.ComponentGroup
import cn.motui.devtools.ui.selected
import cn.motui.devtools.util.Notifications
import com.intellij.ui.components.JBLabel
import icons.Icons
import org.apache.commons.lang3.math.NumberUtils
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import javax.swing.Icon
import javax.swing.JComponent

/**
 * 时间组件
 */
class TimeComponent : TimeForm(), DevToolsComponent {
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    init {
        nowLabel.text = messagePointer("component.time.now.label")
        // 每秒执行一次
        Timer().schedule(TimeTask(nowTimeLabel, nowTimeFormatLabel, dateTimeFormatter), 0, 1000)
        timestampLabel.text = messagePointer("component.time.timestamp.label")
        val now = LocalDateTime.now()
        // 时间戳
        timestampField.text = now.toTimestamp().toString()
        Unit.values().forEach { timestampUnitComboBox.addItem(it) }
        timestampUnitComboBox.selectedIndex = 0
        timestampFormatField.text = now.format(dateTimeFormatter)
//        timestampFormatField.addFocusListener()
        timestampButton.text = messagePointer("component.time.transform.button")
        timestampButton.addActionListener {
            val text = timestampField.text
            val unit = timestampUnitComboBox.selected ?: Unit.MILLISECOND
            if (checkTimestamp(text, unit)) {
                timestampFormatField.text = text.toLong().toLocalDateTime().format(dateTimeFormatter)
            } else {
                Notifications.showErrorNotification(
                    NOTIFICATIONS_ID,
                    "",
                    messagePointer("component.time.timestamp.error")
                )
            }
        }

        // 时间字符串
        timeFormatLabel.text = messagePointer("component.time.format.label")
        timeFormatField.text = now.format(dateTimeFormatter)
        timeFormatTimestampFiled.text = now.toTimestamp().toString()
        Unit.values().forEach { timeFormatUnitComboBox.addItem(it) }
        timeFormatUnitComboBox.selectedIndex = 0
        formatButton.text = messagePointer("component.time.transform.button")
        formatButton.addActionListener {
            val text = timeFormatField.text
            if (text.isNotEmpty()) {
                val unit = timeFormatUnitComboBox.selected ?: Unit.MILLISECOND
                try {
                    val localDateTime = LocalDateTime.parse(text, dateTimeFormatter)
                    timeFormatTimestampFiled.text = localDateTime.toTimestamp(unit).toString()
                } catch (e: Exception) {
                    Notifications.showErrorNotification(
                        NOTIFICATIONS_ID,
                        "",
                        messagePointer("component.time.time.str.error")
                    )
                }
            }
        }
    }

    private fun checkTimestamp(text: String, unit: Unit): Boolean {
        if (text.isNotEmpty() && NumberUtils.isCreatable(text) && text.length == unit.length) {
            return true
        }
        return false
    }

    class TimeTask constructor(
        private val nowTimeLabel: JBLabel,
        private val nowTimeFormatLabel: JBLabel,
        private val dateTimeFormatter: DateTimeFormatter
    ) : TimerTask() {
        override fun run() {
            val now = LocalDateTime.now()
            nowTimeLabel.text = now.toTimestamp().toString()
            nowTimeFormatLabel.text = now.format(dateTimeFormatter)
        }
    }

    override fun componentId(): String {
        return "time"
    }

    override fun name(): String {
        return messagePointer("component.time.name")
    }

    override fun description(): String {
        return messagePointer("component.time.description")
    }

    override fun icon(): Icon {
        return Icons.Component.Time
    }

    override fun componentGroup(): ComponentGroup {
        return ComponentGroup.DEFAULT
    }

    override fun content(): JComponent {
        return root()
    }
}

fun LocalDateTime.toTimestamp(): Long {
    val zoneOffset = ZoneOffset.of("+8")
    return this.toInstant(zoneOffset).toEpochMilli()
}

fun LocalDateTime.toTimestamp(unit: Unit): Long {
    val zoneOffset = ZoneOffset.of("+8")
    return if (Unit.MILLISECOND == unit) this.toInstant(zoneOffset).toEpochMilli() else this.toEpochSecond(zoneOffset)
}

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
}

enum class Unit constructor(
    private val displayName: String,
    val length: Int
) {
    MILLISECOND(messagePointer("component.time.unit.millisecond"), 13),
    SECOND(messagePointer("component.time.unit.second"), 10);

    override fun toString(): String {
        return displayName
    }
}