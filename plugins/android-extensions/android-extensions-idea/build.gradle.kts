
description = "Kotlin Android Extensions IDEA"

apply { plugin("kotlin") }

val androidSdk by configurations.creating

dependencies {
    compile(project(":compiler:util"))
    compile(project(":compiler:light-classes"))
    compile(project(":idea:idea-core"))
    compile(project(":idea"))
    compile(project(":idea:idea-gradle"))
    compile(project(":plugins:android-extensions-compiler"))
    compileOnly(project(":kotlin-android-extensions-runtime"))
    compileOnly(intellijPluginDep("android")) { includeJars("android.jar", "android-common.jar", "sdk-common.jar", "sdk-tools.jar") }
    compileOnly(intellijPluginDep("Groovy")) { includeJars("Groovy.jar") }
    compileOnly(intellijDep()) { includeJars("extensions.jar", "openapi.jar", "util.jar", "idea.jar") }

    testCompile(project(":compiler:tests-common"))
    testCompile(project(":compiler:cli"))
    testCompile(project(":compiler:frontend.java"))
    testCompile(project(":idea:idea-test-framework")) { isTransitive = false }
    testCompile(project(":plugins:kapt3-idea"))
    testCompile(projectTests(":compiler:tests-common"))
    testCompile(projectTests(":idea"))
    testCompile(projectTests(":idea:idea-android"))
    testCompile(projectDist(":kotlin-test:kotlin-test-jvm"))
    testCompile(commonDep("junit:junit"))
    testRuntime(projectDist(":kotlin-reflect"))
    testCompile(intellijPluginDep("android")) { includeJars("android.jar", "android-common.jar", "sdk-common.jar", "sdk-tools.jar") }
    testCompile(intellijPluginDep("Groovy")) { includeJars("Groovy.jar") }
    testCompile(intellijDep()) { includeJars("extensions.jar") }

    testRuntime(project(":idea:idea-jvm"))
    testRuntime(project(":plugins:android-extensions-jps"))
    testRuntime(project(":sam-with-receiver-ide-plugin"))
    testRuntime(project(":noarg-ide-plugin"))
    testRuntime(project(":allopen-ide-plugin"))
    testRuntime(project(":plugins:lint"))
    testRuntime(intellijDep())
    testRuntime(intellijPluginDep("junit"))
    testRuntime(intellijPluginDep("IntelliLang"))
    testRuntime(intellijPluginDep("testng"))
    testRuntime(intellijPluginDep("copyright"))
    testRuntime(intellijPluginDep("properties"))
    testRuntime(intellijPluginDep("java-i18n"))
    testRuntime(intellijPluginDep("gradle"))
    testRuntime(intellijPluginDep("Groovy"))
    testRuntime(intellijPluginDep("coverage"))
    testRuntime(intellijPluginDep("java-decompiler"))
    testRuntime(intellijPluginDep("maven"))
    testRuntime(intellijPluginDep("android"))

    androidSdk(project(":custom-dependencies:android-sdk", configuration = "androidSdk"))
}

sourceSets {
    "main" { projectDefault() }
    "test" { projectDefault() }
}

testsJar {}

projectTest {
    dependsOn(":kotlin-android-extensions-runtime:dist")
    dependsOn(androidSdk)
    workingDir = rootDir
    doFirst {
        systemProperty("android.sdk", androidSdk.singleFile.canonicalPath)
    }
}

runtimeJar()

ideaPlugin()
