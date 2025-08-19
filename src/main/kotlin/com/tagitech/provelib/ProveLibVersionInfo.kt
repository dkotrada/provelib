package com.tagitech.provelib

data class ProveLibVersionInfo(
    val version: String,
    val gitHash: String
) {
    companion object {
        const val DEFAULT_VERSION = "0.0.0"
        const val DEFAULT_GIT_HASH = "0a0a0a0a"
    }
}
