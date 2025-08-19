package com.tagitech.provelib

object Utils {
    @JvmStatic
    fun getProveLibVersion(): ProveLibVersionInfo {
        val pkg = Utils::class.java.`package`
        val version = pkg.implementationVersion ?: ProveLibVersionInfo.DEFAULT_VERSION

        // Check system property first (for tests)
        val gitHashFromProperty = System.getProperty("expectedGitHash")
        val gitHash = if (!gitHashFromProperty.isNullOrBlank()) {
            gitHashFromProperty
        } else {
            // Read Git-Hash attribute from manifest (for production)
            val manifestStream = Utils::class.java.getResourceAsStream("/META-INF/MANIFEST.MF")
            manifestStream?.bufferedReader()?.useLines { lines ->
                lines.firstOrNull { it.startsWith("Git-Hash:") }
                     ?.substringAfter("Git-Hash: ")?.trim()
            } ?: ProveLibVersionInfo.DEFAULT_GIT_HASH
        }

        return ProveLibVersionInfo(version, gitHash)
    }

    @JvmStatic
    fun printProveLibVersion(): String {
        val info = getProveLibVersion()
        return "ProveLib ${info.version} (git: ${info.gitHash})"
    }
}
