package com.example.hrkotlin

import android.app.Application
import com.example.hrkotlin.base.log.Logger
import com.example.hrkotlin.common.pref.SharePrefManager

class MainActivity : Application() {
    companion object {
        private var sAppInstance: MainActivity? = null

        @Synchronized
        fun setAppInstance(instance: MainActivity) {
            sAppInstance = instance
        }

        fun getInstance(): MainActivity? {
            return sAppInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        setAppInstance(this)
    }
}