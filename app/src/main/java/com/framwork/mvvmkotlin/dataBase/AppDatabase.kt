package com.framwork.mvvmkotlin.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.framwork.mvvmkotlin.rest.voResponse.Datum


@Database(entities = [User::class, Datum::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}