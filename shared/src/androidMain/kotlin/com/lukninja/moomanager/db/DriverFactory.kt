package com.lukninja.moomanager.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.moomanager.database.MooManagerDatabase

lateinit var appContext: Context

actual fun createDriver(): SqlDriver {
    return AndroidSqliteDriver(MooManagerDatabase.Schema, com.lukninja.moomanager.db.appContext, "yourappdatabasename.db")
}