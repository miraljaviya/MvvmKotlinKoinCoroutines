package com.framwork.mvvmkotlin.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
    @field:PrimaryKey(autoGenerate = true)
    val id: Int,
    var email: String,
    var pass: String
)