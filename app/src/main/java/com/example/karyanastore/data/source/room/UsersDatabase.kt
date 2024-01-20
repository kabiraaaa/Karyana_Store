package com.example.karyanastore.data.source.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.karyanastore.domain.models.Users

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UsersDao

    companion object {
        private var INSTANCE: UsersDatabase? = null

        fun getIUsersDatabase(context: Context): UsersDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, UsersDatabase::class.java, "productsDB")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}