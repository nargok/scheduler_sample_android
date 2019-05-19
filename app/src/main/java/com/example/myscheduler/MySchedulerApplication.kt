package com.example.myscheduler

import android.app.Application
import io.realm.Realm

// アプリケーション起動したときにRealmのセットアップをする
class MySchedulerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}