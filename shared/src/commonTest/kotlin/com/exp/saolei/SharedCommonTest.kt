package com.exp.saolei

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SharedCommonTest {

    @Test
    fun testAppInfoVersion() {
        assertTrue(AppInfo.VERSION_NAME.isNotEmpty())
        assertTrue(AppInfo.VERSION_CODE > 0)
        assertEquals("v${AppInfo.VERSION_NAME}", AppInfo.FULL_VERSION)
        assertEquals("扫雷", AppInfo.APP_NAME)
        assertEquals("0.0.3", AppInfo.VERSION_NAME)
        assertEquals(3, AppInfo.VERSION_CODE)
    }
}
