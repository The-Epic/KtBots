plugins {
  kotlin("jvm") version "2.4.0"
  id("com.gradleup.shadow") version "9.6.1"
}

group = "xyz.epicebic"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven("https://repo.opencollab.dev/main/")
}

dependencies {
  testImplementation(kotlin("test"))
  implementation("org.geysermc.mcprotocollib:protocol:1.21.11-1")
  implementation("ch.qos.logback:logback-classic:1.5.37")
  implementation("org.jline:jline:4.0.0")
  implementation("net.sf.jopt-simple:jopt-simple:6.0-alpha-3")
}

kotlin {
  jvmToolchain(21)
}

tasks.test {
  useJUnitPlatform()
}

tasks.jar {
  manifest {
    attributes["Main-Class"] = "xyz.epicebic.ktbots.MainKt"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  }
}

tasks.shadowJar {
  archiveClassifier.set("")
}
