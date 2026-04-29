package com.tracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tracker.ui.TrackerViewModel

private val ACTIVITIES = listOf(
    "Exercise", "Work", "Social", "Outdoors", "Reading",
    "Screen Time", "Meditation", "Sleep", "Cooking", "Music"
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LogScreen(viewModel: TrackerViewModel, onSaved: () -> Unit) {
    var energy by remember { mutableIntStateOf(3) }
    var mood by remember { mutableIntStateOf(3) }
    var selectedActivities by remember { mutableStateOf(setOf<String>()) }
    var notes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Log Entry", style = MaterialTheme.typography.headlineMedium)

        RatingRow(label = "Energy", value = energy, onValueChange = { energy = it })
        RatingRow(label = "Mood", value = mood, onValueChange = { mood = it })

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Activities", style = MaterialTheme.typography.titleMedium)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ACTIVITIES.forEach { activity ->
                    FilterChip(
                        selected = activity in selectedActivities,
                        onClick = {
                            selectedActivities = if (activity in selectedActivities)
                                selectedActivities - activity
                            else
                                selectedActivities + activity
                        },
                        label = { Text(activity) }
                    )
                }
            }
        }

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes (optional)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )

        Button(
            onClick = {
                viewModel.saveEntry(energy, mood, selectedActivities.toList(), notes)
                energy = 3
                mood = 3
                selectedActivities = emptySet()
                notes = ""
                onSaved()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

@Composable
private fun RatingRow(label: String, value: Int, onValueChange: (Int) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            (1..5).forEach { i ->
                FilterChip(
                    selected = i == value,
                    onClick = { onValueChange(i) },
                    label = { Text(i.toString()) }
                )
            }
        }
    }
}
