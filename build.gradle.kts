plugins {
    `java-library`
    id("com.gradleup.shadow") version("9.3.1")
    id("xyz.jpenilla.run-paper") version("3.0.2")
}

group = "org.lushplugins"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.papermc.io/repository/maven-public/") // Paper
    maven("https://repo.codemc.io/repository/maven-snapshots/") // PacketEvents
    maven("https://maven.pvphub.me/tofaa") // EntityLib
    maven("https://repo.lushplugins.org/snapshots/") // ConfigurableNPCs, LushLib
}

dependencies {
    // Dependencies
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("com.github.retrooper:packetevents-spigot:2.11.3-SNAPSHOT")

    // Libraries
    implementation("io.github.tofaa2:spigot:3.3.2-SNAPSHOT")
    implementation("org.lushplugins:ConfigurableNPCs:0.0.16")
    implementation("org.lushplugins:LushLib:1.0.0")
    implementation("org.lushplugins.guihandler:GuiHandler:3.0.1")
    implementation("org.lushplugins.guihandler:GuiHandler-config:3.0.1")
    implementation("io.github.revxrsal:lamp.common:4.0.0-rc.16")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-rc.16")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))

    registerFeature("optional") {
        usingSourceSet(sourceSets["main"])
    }

    withSourcesJar()
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    shadowJar {
        minimize()

        archiveFileName.set("${project.name}-${project.version}.jar")
    }

    processResources{
        filesMatching("plugin.yml") {
            expand(project.properties)
        }

        inputs.property("version", rootProject.version)
        filesMatching("plugin.yml") {
            expand("version" to rootProject.version)
        }
    }

    runServer {
        minecraftVersion("1.21.11")

        downloadPlugins {
            modrinth("packetevents", "2.11.2+spigot")
            modrinth("viaversion", "5.7.1")
            modrinth("viabackwards", "5.7.1")
        }
    }
}
