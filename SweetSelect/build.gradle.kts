plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    id("com.vanniktech.maven.publish") version "0.36.0"}


group = "io.github.sosauce"
version = "1.0.0"

android {
    namespace = "com.sosauce.sweetselect.compose"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(21)
    }
    buildFeatures {
        compose = true
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
}

mavenPublishing {
    coordinates(group.toString(), "sweetselect-compose", version.toString())

    pom {
        name.set("Sweet Select")
        description.set("A Compose library that makes multi-selection really easy and highly optimized, with support for a finite amount of selectable elements!")
        inceptionYear.set("2026")
        url.set("https://github.com/sosauce/sweetselect/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("sosauce")
                name.set("sosauce")
                url.set("https://github.com/sosauce/")
            }
        }
        scm {
            url.set("https://github.com/sosauce/sweetselect/")
            connection.set("scm:git:git://github.com/sosauce/sweetselect.git")
            developerConnection.set("scm:git:ssh://git@github.com/sosauce/sweetselect.git")
        }
    }
}
