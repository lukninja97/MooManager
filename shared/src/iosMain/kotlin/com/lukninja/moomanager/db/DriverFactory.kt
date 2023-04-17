package com.lukninja.moomanager.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.moomanager.database.MooManagerDatabase

actual fun createDriver(): SqlDriver {
    return NativeSqliteDriver(MooManagerDatabase.Schema, "yourappdatabasename.db")
}