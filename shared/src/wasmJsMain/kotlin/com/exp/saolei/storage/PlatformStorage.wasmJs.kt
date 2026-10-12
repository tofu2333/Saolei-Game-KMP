package com.exp.saolei.storage

actual object PlatformStorage {
    private val memoryStore = mutableMapOf<String, String>()

    actual fun getString(key: String, defaultValue: String): String {
        return memoryStore[key] ?: defaultValue
    }

    actual fun putString(key: String, value: String) {
        memoryStore[key] = value
    }
}
