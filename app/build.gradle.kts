plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.parcelizeKotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "ar.edu.unicen.seminarioentregable"
    compileSdk = 34

    defaultConfig {
        applicationId = "ar.edu.unicen.seminarioentregable"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf("-Xjvm-default=all")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        dataBinding = true
    }

    kapt {
        correctErrorTypes = true
        useBuildCache = false
    }

    kotlin {
        sourceSets {
            debug {
                kotlin.srcDir("build/generated/source/kaptKotlin/debug")
            }
            release {
                kotlin.srcDir("build/generated/source/kaptKotlin/release")
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activityKtx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.hilt)
    implementation(libs.androidx.tracing.perfetto.handshake)
    implementation(libs.play.services.basement)
    implementation(libs.androidx.runner)
    kapt(libs.hilt.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.glide)

    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    implementation("androidx.paging:paging-runtime:3.3.2")

    implementation("androidx.room:room-runtime:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    implementation("androidx.fragment:fragment-ktx:1.5.7")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}