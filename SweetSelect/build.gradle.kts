plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    id("maven-publish")
}

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

group = "com.github.sosauce"
version = "1.0.0"

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.sosauce"
                artifactId = "sweetselect"
            }
        }
    }
}
