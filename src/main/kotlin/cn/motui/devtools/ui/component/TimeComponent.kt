package cn.motui.devtools.ui.component

import cn.motui.devtools.DevToolsBundle.messagePointer
import cn.motui.devtools.NOTIFICATIONS_ID
import cn.motui.devtools.ui.component.action.ComponentGroup
import cn.motui.devtools.ui.selected
import cn.motui.devtools.util.Notifications
import com.intellij.icons.AllIcons
import com.intellij.ui.components.JBLabel
import icons.Icons
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

    init {
        nowLabel.text = messagePointer("component.time.now.label")
        // 每秒执行一次
        Timer().schedule(TimeTask(nowTimeLabel, nowTimeFormatLabel, dateTimeFormatter), 0, 1000)
        timestampLabel.text = messagePointer("component.time.timestamp.label")
        val now = LocalDateTime.now()
        // 时间戳
        timestampField.text = now.toTimestamp().toString()
        Unit.values().forEach { unitComboBox.addItem(it) }
        unitComboBox.selectedIndex = 0
        timestampButton.text = messagePointer("component.time.transform.format.button")
        timestampButton.icon = AllIcons.Actions.NextOccurence
        formatButton.text = messagePointer("component.time.transform.timestamp.button")
        formatButton.icon = AllIcons.Actions.PreviousOccurence
        Utc.values().forEach { utcComboBox.addItem(it) }
        // 默认 UTC+8:00
        utcComboBox.selectedIndex = 20
        formatField.text = now.format(dateTimeFormatter)
        formatLabel.text = messagePointer("component.time.format.label")
        timestampButton.addActionListener {
            var text = timestampField.text
            val utc = utcComboBox.selected ?: Utc.UTC_PLUS_8
            val check = text.isNotBlank() && (text.length == 10 || text.length == 13)
            if (check) {
                // 秒转毫秒
                text = if (text.length == 10) text + "000" else text
                formatField.text = text.toLong().toLocalDateTime(utc.value).format(dateTimeFormatter)
            } else {
                Notifications.showErrorNotification(
                    NOTIFICATIONS_ID,
                    "",
                    messagePointer("component.time.timestamp.error")
                )
            }
        }
        formatButton.addActionListener {
            val text = formatField.text
            if (text.isNotEmpty()) {
                val unit = unitComboBox.selected ?: Unit.MILLISECOND
                val utc = utcComboBox.selected ?: Utc.UTC_PLUS_8
                toTimestamp(text, unit, utc)?.let {
                    timestampField.text = it.toString()
                }
            }
        }
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
        return "f61b7e23b803413f8232c31792c78e4a"
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

    companion object {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        fun toTimestamp(text: String, unit: Unit, utc: Utc): Long? {
            val replace = text.replace("/", "-")
                .replace("_", "-")
                .replace("年", "-")
                .replace("月", "-")
                .replace("日", "")
            try {
                val localDateTime = LocalDateTime.parse(replace, dateTimeFormatter)
                return localDateTime.toTimestamp(unit, utc.value)
            } catch (e: Exception) {
                Notifications.showErrorNotification(
                    NOTIFICATIONS_ID,
                    "",
                    messagePointer("component.time.time.str.error")
                )
            }
            return null
        }
    }
}

fun LocalDateTime.toTimestamp(): Long {
    val zoneOffset = ZoneOffset.of("+8")
    return this.toInstant(zoneOffset).toEpochMilli()
}

fun LocalDateTime.toTimestamp(unit: Unit, offsetId: String): Long {
    val zoneOffset = ZoneOffset.of(offsetId)
    return if (Unit.MILLISECOND == unit) this.toInstant(zoneOffset).toEpochMilli() else this.toEpochSecond(zoneOffset)
}

fun Long.toLocalDateTime(offsetId: String): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.of(offsetId))
}

enum class Unit constructor(
    private val displayName: String,
) {
    MILLISECOND(messagePointer("component.time.unit.millisecond")),
    SECOND(messagePointer("component.time.unit.second"));

    override fun toString(): String {
        return displayName
    }
}

enum class Utc constructor(
    private val displayName: String,
    val value: String
) {
    UTC_LESS_12("UTC-12:00", "-12"),
    UTC_LESS_11("UTC-11:00", "-11"),
    UTC_LESS_10("UTC-10:00", "-10"),
    UTC_LESS_9("UTC-9:00", "-9"),
    UTC_LESS_8("UTC-8:00", "-8"),
    UTC_LESS_7("UTC-7:00", "-7"),
    UTC_LESS_6("UTC-6:00", "-6"),
    UTC_LESS_5("UTC-5:00", "-5"),
    UTC_LESS_4("UTC-4:00", "-4"),
    UTC_LESS_3("UTC-3:00", "-3"),
    UTC_LESS_2("UTC-2:00", "-2"),
    UTC_LESS_1("UTC-1:00", "-1"),
    UTC_0("UTC±0:00", "-0"),
    UTC_PLUS_1("UTC+1:00", "+1"),
    UTC_PLUS_2("UTC+2:00", "+2"),
    UTC_PLUS_3("UTC+3:00", "+3"),
    UTC_PLUS_4("UTC+4:00", "+4"),
    UTC_PLUS_5("UTC+5:00", "+5"),
    UTC_PLUS_6("UTC+6:00", "+6"),
    UTC_PLUS_7("UTC+7:00", "+7"),
    UTC_PLUS_8("UTC+8:00", "+8"),
    UTC_PLUS_9("UTC+9:00", "+9"),
    UTC_PLUS_10("UTC+10:00", "+10"),
    UTC_PLUS_11("UTC+11:00", "+11"),
    UTC_PLUS_12("UTC+12:00", "+12"),
    ;

    override fun toString(): String {
        return displayName
    }
}