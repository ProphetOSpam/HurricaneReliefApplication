plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {

}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

println(sourceSets.main);

application {
    mainClass = "Main"
}
