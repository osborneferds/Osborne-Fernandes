package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ExchangedContact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM exchanged_contacts ORDER BY createdAt DESC")
    fun getAllContacts(): Flow<List<ExchangedContact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ExchangedContact): Long

    @Delete
    suspend fun deleteContact(contact: ExchangedContact)

    @Query("DELETE FROM exchanged_contacts WHERE id = :id")
    suspend fun deleteById(id: Long)
}
