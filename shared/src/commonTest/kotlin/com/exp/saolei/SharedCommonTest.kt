package com.exp.saolei

import kotlin.test.Test
import kotlin.test.assertEquals

class SharedCommonTest {

    @Test
    fun testAppInfoVersion() {
        assertEquals("0.0.1", AppInfo.VERSION_NAME)
        assertEquals(1, AppInfo.VERSION_CODE)
        assertEquals("v0.0.1", AppInfo.FULL_VERSION)
        assertEquals("扫雷", AppInfo.APP_NAME)
    }
}