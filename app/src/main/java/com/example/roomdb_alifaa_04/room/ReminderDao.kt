package com.example.roomdb_alifaa_04.room

import androidx.room.*

@Dao
interface ReminderDao {
    @Insert
    suspend fun addReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM reminder")
    suspend fun getReminders():List<Reminder>

    @Query("SELECT * FROM reminder WHERE id=:reminder_id")
    suspend fun getReminder(reminder_id: Int):List<Reminder>
}