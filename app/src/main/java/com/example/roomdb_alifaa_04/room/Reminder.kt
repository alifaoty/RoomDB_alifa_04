package com.example.roomdb_alifaa_04.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String
)