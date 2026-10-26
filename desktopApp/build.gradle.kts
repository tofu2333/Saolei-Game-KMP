import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(project(":shared"))

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)
}

compose.desktop {
    application {
        mainClass = "com.exp.saolei.MainKt"
        val localJavaHome = file("/Users/tofu/Library/Java/JavaVirtualMachines/ms-21.0.8/Contents/Home")
        if (localJavaHome.exists()) {
            javaHome = localJavaHome.absolutePath
        }

        buildTypes.release.proguard {
            isEnabled.set(false)
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "Saolei"
            packageVersion = "1.0.0"
            description = "Saolei Minesweeper"
            modules("java.base", "java.desktop", "java.logging", "jdk.unsupported")
            macOS {
                iconFile.set(project.file("src/main/resources/icon.icns"))
                bundleID = "com.exp.saolei"
            }
            windows {
                iconFile.set(project.file("src/main/resources/icon.ico"))
                menuGroup = "Saolei"
            }
            linux {
                iconFile.set(project.file("src/main/resources/icon.png"))
            }
        }
    }
}