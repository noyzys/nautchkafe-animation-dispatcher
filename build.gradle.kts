plugins {
    id("java")
    kotlin("jvm") version "2.2.21"
}

group = "dev.nautchkafe.animation"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    // fp stuff
    implementation("io.vavr:vavr:0.10.7")

    // minecraft server-side software stuff
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.20.1-experimental-SNAPSHOT")

    // kyori stuff
    implementation("net.kyori.adventure:adventure-api:4.11.0")
    implementation("net.kyori:adventure-platform-bukkit:4.4.1")
    implementation("net.kyori.adventure:adventure-text-minimessage:4.11.0")

    // tests stuff
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.17.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
}

tasks.test {
    useJUnitPlatform()
}
