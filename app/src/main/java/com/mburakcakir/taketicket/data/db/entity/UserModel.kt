package com.mburakcakir.taketicket.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_user")
data class UserModel(
    val name: String,
    val userName: String,
    val email: String,
    val password: String,
    val profileImageUri: String?
){
    @PrimaryKey(autoGenerate = true)
    var userID : Int = 0
}