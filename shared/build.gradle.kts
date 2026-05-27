plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("app.cash.sqldelight")
}

android {
    namespace = "app.valenceyou.shared"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        targetSdk = 35
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("ValenceYouDatabase") {
            packageName.set("app.valenceyou.shared")
        }
    }
}

dependencies {
    implementation("app.cash.sqldelight:android-driver:2.0.2")
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}
