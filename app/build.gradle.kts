plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp") // KSP is replacing Kapt
}

android {
    namespace = "com.udemy.jettrivia"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.udemy.jettrivia"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // coroutines
    val coroutinesVersion = "1.7.3" // 1.5.2 in course
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${coroutinesVersion}")

    // coroutines Lifecycle Scopes
    val coroutinesLifecycleScopeVersion = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${coroutinesLifecycleScopeVersion}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${coroutinesLifecycleScopeVersion}")

    //dagger-hilt
    val daggerVersion = "2.50"  //2.39 in course
    implementation("com.google.dagger:hilt-android-gradle-plugin:${daggerVersion}")
    implementation("com.google.dagger:hilt-android:${daggerVersion}")
    //implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0") //1.0.0-alpha03 in course
    //kapt("com.google.dagger:hilt-compiler:${daggerVersion}")
    //kapt("androidx.hilt:hilt-compiler:1.1.0") // 1.0.0 in course
    ksp("com.google.dagger:hilt-compiler:${daggerVersion}")
    ksp("androidx.hilt:hilt-compiler:1.1.0") // 1.0.0 in course

    // Retrofit API
    val retrofitVersion = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    // GSON Converter
    implementation ("com.squareup.retrofit2:converter-gson:${retrofitVersion}")
}