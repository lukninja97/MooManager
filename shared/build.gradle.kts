import com.lukninja.dependencies.Deps
import com.lukninja.dependencies.Version

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
    id("com.android.library")
}

version = "1.0"

sqldelight {
    database("MooManagerDatabase") {
        packageName = "com.moomanager.database"
        sourceFolders = listOf("sqldelight")
    }
}


kotlin {
    android()


    val iosTarget: (String, org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit) -> org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
        else -> ::iosX64
    }

    iosTarget("ios") {}

    cocoapods {
        summary = "Your App KMM Shared Module"
        homepage = "N/A"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            export(com.lukninja.dependencies.Deps.Log.kermit)
            isStatic = true
        }

        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] =
            org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] =
            org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
    }


    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(Deps.Kotlin.dateTime)
                implementation(Deps.Ktor.ktorClientCore)
                implementation(Deps.Ktor.ktorClientSerialization)
                implementation(Deps.Ktor.ktorLog)
                implementation(Deps.Kotlin.coroutinesShared){
                    version{
                        strictly(Version.kotlinCoroutinesVersion)
                    }
                }
                implementation(Deps.Kotlin.serialization)
                implementation(Deps.SqlDelight.sqlDelightRuntime)
                implementation(Deps.SqlDelight.sqlDelightCoroutineExtensions)
                api(Deps.Log.kermit)
                implementation(Deps.Log.kermitCrashlytics)
                implementation(Deps.Touchlab.commonLib)
                implementation(Deps.Koin.koinCore)
                implementation(Deps.Ktor.ktorContentNegotiation)
                implementation(Deps.Ktor.ktorJson)
                implementation(Deps.Ktor.ktorEncoding)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }


        val androidMain by getting {
            dependencies {
                implementation(Deps.Kotlin.kotlinStdLib)
                implementation(Deps.Ktor.ktorClientAndroid)
                implementation(Deps.SqlDelight.sqlDelightAndroid)
                implementation(Deps.Klock.klockLib)
                implementation(Deps.Koin.koinClientAndroid)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }


        val iosMain by getting {
            dependencies {
                implementation(Deps.Ktor.ktorClientIos)
                implementation(Deps.SqlDelight.sqlDelightIos)
                api(Deps.Touchlab.crashKiOSClient)
            }
        }
    }
}


kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
    binaries.all {
        binaryOptions["memoryModel"] = "experimental"
        binaryOptions["freezing"] = "disabled"
    }
}


android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}