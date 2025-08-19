package com.tagitech.provelib

import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {

    @Test
    fun testGetProveLibVersionStrict() {
        val expectedVersion = System.getProperty("expectedVersion") ?: ProveLibVersionInfo.DEFAULT_VERSION
        val expectedGit = System.getProperty("expectedGitHash") ?: ProveLibVersionInfo.DEFAULT_GIT_HASH
        val info = Utils.getProveLibVersion()

        assertEquals(expectedVersion, info.version, "Version should match project.version")
        assertEquals(expectedGit, info.gitHash, "Git hash should match current repo hash or default")
    }
}
