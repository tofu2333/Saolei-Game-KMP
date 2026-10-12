package com.exp.saolei.storage

import android.content.Context
import android.content.SharedPreferences

actual object PlatformStorage {
    private var sharedPrefs: SharedPreferences? = null
    private val memoryFallback = mutableMapOf<String, String>()

    fun init(context: Context) {
        if (sharedPrefs == null) {
            sharedPrefs = context.applicationContext.getSharedPreferences("saolei_prefs", Context.MODE_PRIVATE)
        }
    }

    actual fun getString(key: String, defaultValue: String): String {
        return sharedPrefs?.getString(key, defaultValue) ?: memoryFallback[key] ?: defaultValue
    }

    actual fun putString(key: String, value: String) {
        memoryFallback[key] = value
        sharedPrefs?.edit()?.putString(key, value)?.apply()
    }
}
