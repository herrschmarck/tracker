package com.tracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tracker.ui.TrackerViewModel
import com.tracker.ui.screens.HistoryScreen
import com.tracker.ui.screens.LogScreen

@Composable
fun TrackerApp(vm: TrackerViewModel = viewModel()) {
    var tab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                    label = { Text("Log") },
                    selected = tab == 0,
                    onClick = { tab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Menu, contentDescription = null) },
                    label = { Text("History") },
                    selected = tab == 1,
                    onClick = { tab = 1 }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (tab) {
                0 -> LogScreen(viewModel = vm, onSaved = { tab = 1 })
                1 -> HistoryScreen(viewModel = vm)
            }
        }
    }
}
