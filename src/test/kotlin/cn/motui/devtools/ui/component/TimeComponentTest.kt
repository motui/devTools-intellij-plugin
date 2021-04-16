package cn.motui.devtools.ui.component

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class TimeComponentTest {

    @Test
    fun toTimestamp() {
        var timestamp = TimeComponent.toTimestamp("2020-01-02 13:11:22", Unit.MILLISECOND, Utc.UTC_PLUS_8)
        assertNotNull(timestamp)
        assertEquals(1577941882000, timestamp)
        timestamp = TimeComponent.toTimestamp("2020/01/02 13:11:22", Unit.MILLISECOND, Utc.UTC_PLUS_8)
        assertNotNull(timestamp)
        assertEquals(1577941882000, timestamp)
        timestamp = TimeComponent.toTimestamp("2020年01月02日 13:11:22", Unit.MILLISECOND, Utc.UTC_PLUS_8)
        assertNotNull(timestamp)
        assertEquals(1577941882000, timestamp)
        timestamp = TimeComponent.toTimestamp("2020 01 02 13:11:22", Unit.MILLISECOND, Utc.UTC_PLUS_8)
        assertNotNull(timestamp)
        assertEquals(1577941882000, timestamp)
    }
}