package com.tracker.data

import kotlinx.coroutines.flow.Flow

class TrackerRepository(private val dao: EntryDao) {
    val entries: Flow<List<Entry>> = dao.getAllEntries()

    suspend fun insert(entry: Entry) = dao.insert(entry)
    suspend fun update(entry: Entry) = dao.update(entry)
    suspend fun delete(entry: Entry) = dao.delete(entry)
}
