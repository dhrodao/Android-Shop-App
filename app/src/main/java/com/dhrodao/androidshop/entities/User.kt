package com.dhrodao.androidshop.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val username: String,
    val password: String,
    val firstName: String,
    val secondName: String,
    )