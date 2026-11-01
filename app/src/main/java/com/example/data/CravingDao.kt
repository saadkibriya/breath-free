package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CravingDao {
    @Query("SELECT * FROM craving_records ORDER BY timestamp DESC")
    fun getAllCravings(): Flow<List<CravingRecord>>

    @Query("SELECT COUNT(*) FROM craving_records WHERE overcome = 1")
    fun getOvercomeCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCraving(record: CravingRecord): Long

    @Delete
    suspend fun deleteCraving(record: CravingRecord)
}
