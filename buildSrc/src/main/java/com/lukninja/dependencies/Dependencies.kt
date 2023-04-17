package com.lukninja.dependencies

object Version {

    const val kotlin = "1.6.20"
    const val ktorVersion = "2.0.0"
    const val serializationVersion = "1.3.2"
    const val sqlDelightVersion = "1.5.3"
    const val kotlinCoroutinesVersion = "1.6.0-native-mt"
    const val kermitVersion = "1.0.3"
    const val klockVersion = "2.2.2"
    const val crashkIos = "0.4.0"
    const val koin = "3.1.4"
    const val gradleAGPPlugin = "7.0.3"
    const val kotlinDateTimeVersion = "0.3.1"
}

object Deps {
    object Classpath {
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Version.kotlin}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
        const val androidTools = "com.android.tools.build:gradle:${Version.gradleAGPPlugin}"
        const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:${Version.sqlDelightVersion}"
    }

    object Ktor {
        const val ktorClientCore = "io.ktor:ktor-client-core:${Version.ktorVersion}"
        const val ktorClientSerialization = "io.ktor:ktor-client-serialization:${Version.ktorVersion}"
        const val ktorClientIos = "io.ktor:ktor-client-ios:${Version.ktorVersion}"
        const val ktorClientAndroid = "io.ktor:ktor-client-android:${Version.ktorVersion}"
        const val ktorLog = "io.ktor:ktor-client-logging:${Version.ktorVersion}"
        const val ktorContentNegotiation = "io.ktor:ktor-client-content-negotiation:${Version.ktorVersion}"
        const val ktorJson = "io.ktor:ktor-serialization-kotlinx-json:${Version.ktorVersion}"
        const val ktorEncoding =  "io.ktor:ktor-client-encoding:${Version.ktorVersion}"
    }

    object Klock {
        const val klockLib = "com.soywiz.korlibs.klock:klock:${Version.klockVersion}"
    }

    object Koin {
        const val koinCore = "io.insert-koin:koin-core:${Version.koin}"
        const val koinClientAndroid = "io.insert-koin:koin-android:${Version.koin}"
    }

    object Touchlab {
        const val commonLib = "co.touchlab:stately-common:1.1.4"
        const val crashKiOSClient = "co.touchlab:crashkios:${Version.crashkIos}"
    }

    object SqlDelight {
        const val sqlDelightRuntime = "com.squareup.sqldelight:runtime:${Version.sqlDelightVersion}"
        const val sqlDelightCoroutineExtensions =
            "com.squareup.sqldelight:coroutines-extensions:${Version.sqlDelightVersion}"
        const val sqlDelightIos = "com.squareup.sqldelight:native-driver:${Version.sqlDelightVersion}"
        const val sqlDelightAndroid = "com.squareup.sqldelight:android-driver:${Version.sqlDelightVersion}"
    }

    object Log {
        const val kermit = "co.touchlab:kermit:${Version.kermitVersion}"
        const val kermitCrashlytics = "co.touchlab:kermit-crashlytics:${Version.kermitVersion}"
    }

    object Kotlin {
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.serializationVersion}"
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib"
        const val coroutinesShared =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.kotlinCoroutinesVersion}"
        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Version.kotlinDateTimeVersion}"
    }
}