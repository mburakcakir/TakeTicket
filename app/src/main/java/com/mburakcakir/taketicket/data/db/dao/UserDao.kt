package com.mburakcakir.taketicket.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mburakcakir.taketicket.data.db.entity.UserModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userModel : UserModel)

    @Query("SELECT COUNT(*) FROM table_user WHERE userName =:username AND password =:password")
    fun checkIfUserExists(username : String, password: String) : Int

    @Query("SELECT * FROM table_user WHERE userName =:username AND password =:password")
    fun getUserByUsername(username: String, password: String): UserModel


}