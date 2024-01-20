package com.example.karyanastore

import android.app.Application
import com.example.karyanastore.data.repository.UsersRepository
import com.example.karyanastore.data.source.room.UsersDatabase

class StoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}