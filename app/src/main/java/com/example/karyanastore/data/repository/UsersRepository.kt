package com.example.karyanastore.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.karyanastore.data.source.room.UsersDatabase
import com.example.karyanastore.domain.models.Users

class UsersRepository(
    private val usersDatabase: UsersDatabase
) {
    private val userLiveData = MutableLiveData<List<Users>>()


    val users: LiveData<List<Users>>
        get() = userLiveData

    suspend fun addUser(user: Users) {
        usersDatabase.userDao().addUser(user)
        Log.d("RoomDB", "User added successfully: ${user.userName}, ${user.usernumber}")
        Log.d("RoomDB", "${usersDatabase.userDao().getUsers()}")
    }

    suspend fun getUser(){
        val userList = usersDatabase.userDao().getUsers()
        userLiveData.postValue(userList)
    }

    suspend fun updateAmountAndModifiedAt(userId: String, amountToAdd: Int) {
        val currentUser = usersDatabase.userDao().getUserById(userId)
        val newAmount = currentUser.amount + amountToAdd
        val newModifiedAt = System.currentTimeMillis()
        usersDatabase.userDao().updateAmountAndModifiedAt(userId, newAmount, newModifiedAt)
    }
}