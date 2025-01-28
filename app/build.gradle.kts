import com.android.tools.r8.internal.pl

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.kotbros.android_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kotbros.android_app"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.29")

    implementation("com.github.daniel-stoneuk:material-about-library:2.4.2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}