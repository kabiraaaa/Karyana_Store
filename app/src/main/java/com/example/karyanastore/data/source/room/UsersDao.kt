package com.example.karyanastore.data.source.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.karyanastore.domain.models.Users

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: Users)

    @Query("SELECT * FROM users")
    fun getUsers(): List<Users>

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Users>

    @Query("UPDATE users SET amount = :newAmount, modifiedAt = :newModifiedAt WHERE id = :userId")
    fun updateAmountAndModifiedAt(userId: Int, newAmount: Int, newModifiedAt: Long)
}