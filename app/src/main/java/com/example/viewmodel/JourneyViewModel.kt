package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.CravingRecord
import com.example.data.JourneyPreferences
import com.example.data.JourneyProfile
import com.example.widget.WidgetUpdateHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class JourneyViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = JourneyPreferences(application)
    private val database = AppDatabase.getInstance(application)
    private val cravingDao = database.cravingDao()

    private val _profile = MutableStateFlow(prefs.getProfile())
    val profile: StateFlow<JourneyProfile> = _profile.asStateFlow()

    private val _currentTime = MutableStateFlow(System.currentTimeMillis())
    val currentTime: StateFlow<Long> = _currentTime.asStateFlow()

    val cravings: StateFlow<List<CravingRecord>> = cravingDao.getAllCravings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cravingsOvercomeCount: StateFlow<Int> = cravingDao.getOvercomeCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    init {
        // Real-time ticking loop every second for live countdown and savings accumulation
        viewModelScope.launch {
            while (isActive) {
                _currentTime.value = System.currentTimeMillis()
                delay(1000L)
            }
        }
    }

    fun updateProfile(newProfile: JourneyProfile) {
        prefs.saveProfile(newProfile)
        _profile.value = newProfile
        WidgetUpdateHelper.updateAllWidgets(getApplication())
    }

    fun resetQuitDate(newTimestamp: Long = System.currentTimeMillis()) {
        prefs.resetQuitDate(newTimestamp)
        _profile.value = prefs.getProfile()
        WidgetUpdateHelper.updateAllWidgets(getApplication())
    }

    fun logCraving(
        intensity: Int,
        trigger: String,
        note: String = "",
        technique: String = "4-7-8 Breathing",
        overcome: Boolean = true
    ) {
        viewModelScope.launch {
            val record = CravingRecord(
                timestamp = System.currentTimeMillis(),
                intensity = intensity,
                trigger = trigger,
                note = note,
                techniqueUsed = technique,
                overcome = overcome
            )
            cravingDao.insertCraving(record)
            WidgetUpdateHelper.updateAllWidgets(getApplication())
        }
    }

    fun deleteCraving(record: CravingRecord) {
        viewModelScope.launch {
            cravingDao.deleteCraving(record)
        }
    }

    fun syncWidgets() {
        WidgetUpdateHelper.updateAllWidgets(getApplication())
    }
}
