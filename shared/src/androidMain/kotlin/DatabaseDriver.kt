package app.valenceyou.shared

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

fun createDriver(context: Context): SqlDriver {
    return AndroidSqliteDriver(ValenceYouDatabase.Schema, context, "valenceyou.db")
}
