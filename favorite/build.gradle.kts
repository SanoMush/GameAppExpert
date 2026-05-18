plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.expert.gameapp.favorite"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":core"))

    // Dagger (Manual Component)
    implementation("com.google.dagger:dagger:2.51")
    ksp("com.google.dagger:dagger-compiler:2.51")

    // Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    
    // Lifecycle ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    
    // Activity Compose
    implementation("androidx.activity:activity-compose:1.8.2")

    // Hilt (dibutuhkan untuk EntryPointAccessors dan resolve anotasi)
    implementation("com.google.dagger:hilt-android:2.51")
    ksp("com.google.dagger:hilt-compiler:2.51")}

