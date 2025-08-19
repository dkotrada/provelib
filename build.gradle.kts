plugins {
    kotlin("jvm") version "2.0.20"
    `maven-publish`
}

group = "com.tagitech"

val DEFAULT_VERSION = "0.0.0"
val DEFAULT_GIT_HASH = "0a0a0a0a"

// Allow overriding version with `./gradlew publish -Dversion=0.1.0`
val cliVersion: String? = System.getProperty("version")

version = cliVersion ?: DEFAULT_VERSION // is always the latest version `./gradlew build`

if (!cliVersion.isNullOrBlank()) {
    version = cliVersion
}

fun getGitHash(): String {
    return try {
        val proc = ProcessBuilder("git", "rev-parse", "--short", "HEAD")
            .directory(rootProject.projectDir)
            .redirectErrorStream(true)
            .start()
        val text = proc.inputStream.bufferedReader().readText().trim()
        if (proc.waitFor() == 0 && text.isNotBlank()) text
        else DEFAULT_GIT_HASH
    } catch (_: Exception) {
        DEFAULT_GIT_HASH
    }
}

// Add version + git hash to manifest standart java practice
tasks.jar {
    manifest {
        attributes["Implementation-Version"] = project.version
        attributes["Implementation-Title"] = project.name
        attributes["Git-Hash"] = getGitHash()
    }
}

tasks.test {
    dependsOn(tasks.jar) // ensure jar is build before tests
    systemProperty("expectedVersion", project.version.toString())
    val gitHash = getGitHash()
    systemProperty("expectedGitHash", gitHash)
}

// Maven publishing to local repo. Linux or Mac `ls ~/.m2/repository/com/tagitech/provelib`
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }
    repositories {
        mavenLocal()
    }
}

repositories { mavenCentral() }
dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    // later remove this dependency and abstract validation away
    compileOnly("jakarta.validation:jakarta.validation-api:3.0.2")
}
