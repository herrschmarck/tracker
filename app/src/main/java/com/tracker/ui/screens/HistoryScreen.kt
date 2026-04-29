package com.tracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tracker.data.Entry
import com.tracker.ui.TrackerViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val DATE_FORMAT = DateTimeFormatter.ofPattern("MMM d, yyyy  HH:mm")

@Composable
fun HistoryScreen(viewModel: TrackerViewModel) {
    val entries by viewModel.entries.collectAsState()

    if (entries.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No entries yet.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(entries, key = { it.id }) { entry ->
                EntryCard(entry = entry, onDelete = { viewModel.deleteEntry(entry) })
            }
        }
    }
}

@Composable
private fun EntryCard(entry: Entry, onDelete: () -> Unit) {
    val dateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(entry.timestamp),
        ZoneId.systemDefault()
    )

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dateTime.format(DATE_FORMAT),
                    style = MaterialTheme.typography.labelMedium
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete entry")
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Energy: ${entry.energy}/5")
                Text("Mood: ${entry.mood}/5")
            }
            if (entry.activities.isNotEmpty()) {
                Text(
                    text = entry.activities.split(",").joinToString(" · "),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (entry.notes.isNotEmpty()) {
                Text(entry.notes, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
