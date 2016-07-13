package com.learning.english.simple.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.io.BufferedReader
import java.io.InputStreamReader

class CustomDaoMaster(db: SQLiteDatabase?) : DaoMaster(db) {
    class DevOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?) : DaoMaster.DevOpenHelper(context, name, factory) {
        val ctx = context

        override fun onCreate(db: SQLiteDatabase) {
            super.onCreate(db)
            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(InputStreamReader(ctx.getAssets().open("words_list.sql")))
                reader.forEachLine {
                    db.execSQL(it)
                }
            } catch (ex: Exception) {
            } finally {
                if (reader != null) {
                    reader.close()
                }
            }
        }
    }
}