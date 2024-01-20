package com.example.karyanastore.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey
    var id: Int,
    var userName: String,
    var usernumber: String,
    var remarks: String
)
