package com.lukninja.moomanager.db

import com.squareup.sqldelight.db.SqlDriver
import com.moomanager.database.MooManagerDatabase

expect fun createDriver(): SqlDriver

class DriverFactory {
    fun createDatabase(): MooManagerDatabase {
        val driver = com.lukninja.moomanager.db.createDriver()
        return MooManagerDatabase(driver)
    }
}
