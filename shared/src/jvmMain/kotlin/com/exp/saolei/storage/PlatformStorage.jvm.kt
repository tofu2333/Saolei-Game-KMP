package com.exp.saolei.storage

import java.util.prefs.Preferences

actual object PlatformStorage {
    private val prefs: Preferences = Preferences.userRoot().node("com/exp/saolei")

    actual fun getString(key: String, defaultValue: String): String {
        return try {
            prefs.get(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }

    actual fun putString(key: String, value: String) {
        try {
            prefs.put(key, value)
            prefs.flush()
        } catch (e: Exception) {
            // ignore
        }
    }
}
