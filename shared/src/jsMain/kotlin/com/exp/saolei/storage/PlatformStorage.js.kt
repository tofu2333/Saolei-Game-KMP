package com.exp.saolei.storage

import web.storage.localStorage

actual object PlatformStorage {
    actual fun getString(key: String, defaultValue: String): String {
        return try {
            localStorage.getItem(key) ?: defaultValue
        } catch (e: Throwable) {
            defaultValue
        }
    }

    actual fun putString(key: String, value: String) {
        try {
            localStorage.setItem(key, value)
        } catch (e: Throwable) {
            // ignore
        }
    }
}
