package com.learning.english.simple

import android.app.Application
import com.learning.english.simple.db.CustomDaoMaster
import com.learning.english.simple.db.DaoSession

class MyApplication : Application() {

    companion object {
        private var appInstance : MyApplication? = null
        fun getInstance() : MyApplication? {
            return appInstance
        }
    }

    private var daoSession : DaoSession? = null

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        val helper = CustomDaoMaster.DevOpenHelper(this, "les_database", null)
        val db = helper.getWritableDatabase()
        val daoMaster = CustomDaoMaster(db)
        daoSession = daoMaster.newSession()
    }

    fun getDaoSession() : DaoSession? {
        return daoSession
    }

}