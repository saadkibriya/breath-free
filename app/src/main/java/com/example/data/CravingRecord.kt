package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "craving_records")
data class CravingRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val intensity: Int, // 1 to 10
    val trigger: String, // e.g. Stress, Social, After Meal, Boredom, Coffee
    val note: String = "",
    val techniqueUsed: String = "4-7-8 Breathing",
    val overcome: Boolean = true
)
