package com.framwork.mvvmkotlin.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.framwork.mvvmkotlin.rest.voResponse.Datum

@Dao
interface UserDao {
    @get:Query("SELECT * FROM User")
    val getAllUser:List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(vararg data:Datum)

    @get:Query("SELECT * FROM Datum")
    val getAllData:List<Datum>
}