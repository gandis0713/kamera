plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val vcpkgRoot = System.getenv("VCPKG_ROOT") ?: throw IllegalStateException("VCPKG_ROOT is not set")
val androidSDKRootDir = System.getenv("ANDROID_SDK_ROOT") ?: throw IllegalStateException("ANDROID_SDK_ROOT is not set")
val androidNDKVersion = "25.1.8937393"
val sourceDir = "${projectDir}/src/main/cpp"

android {
    namespace = "com.gandis.kamera"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gandis.kamera"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters += "arm64-v8a"
            abiFilters += "armeabi-v7a"
            //    abiFilters += 'x86_64'
            //    abiFilters += 'x86'
        }

        externalNativeBuild {
            cmake {
                arguments(
                    "-DCMAKE_TOOLCHAIN_FILE=${vcpkgRoot}/scripts/buildsystems/vcpkg.cmake",
                    "-DVCPKG_CHAINLOAD_TOOLCHAIN_FILE=${androidSDKRootDir}/ndk/${androidNDKVersion}/build/cmake/android.toolchain.cmake",
                    "-DVCPKG_INSTALLED_DIR=${sourceDir}/externals",
                    "-DANDROID_ARM_NEON=ON",
                    "-DANDROID_STL=c++_shared",
                )
            }
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
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}