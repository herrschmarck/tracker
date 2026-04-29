package com.tracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tracker.data.Entry
import com.tracker.data.TrackerDatabase
import com.tracker.data.TrackerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrackerViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = TrackerRepository(TrackerDatabase.getInstance(app).entryDao())

    val entries: StateFlow<List<Entry>> = repository.entries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun saveEntry(energy: Int, mood: Int, activities: List<String>, notes: String) {
        viewModelScope.launch {
            repository.insert(
                Entry(
                    energy = energy,
                    mood = mood,
                    activities = activities.joinToString(","),
                    notes = notes
                )
            )
        }
    }

    fun deleteEntry(entry: Entry) {
        viewModelScope.launch { repository.delete(entry) }
    }
}
