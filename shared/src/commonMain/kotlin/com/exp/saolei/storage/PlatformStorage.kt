package com.exp.saolei.storage

expect object PlatformStorage {
    fun getString(key: String, defaultValue: String = ""): String
    fun putString(key: String, value: String)
}
