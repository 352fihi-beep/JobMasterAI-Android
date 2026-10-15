package com.jobmaster.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jobmaster.ai.ui.theme.JobMasterAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobMasterAITheme {
                JobMasterApp()
            }
        }
    }
}

@Composable
fun JobMasterApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                    label = { Text("Dashboard") },
                    selected = currentRoute == "dashboard",
                    onClick = {
                        if (currentRoute != "dashboard") {
                            navController.navigate("dashboard") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search") },
                    selected = currentRoute == "search",
                    onClick = {
                        if (currentRoute != "search") {
                            navController.navigate("search") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Description, contentDescription = "Resume") },
                    label = { Text("Resume") },
                    selected = currentRoute == "resume",
                    onClick = {
                        if (currentRoute != "resume") {
                            navController.navigate("resume") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = currentRoute == "settings",
                    onClick = {
                        if (currentRoute != "settings") {
                            navController.navigate("settings") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(padding)
        ) {
            composable("dashboard") { DashboardScreen() }
            composable("search") { SearchScreen() }
            composable("resume") { ResumeScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}

@Composable
fun DashboardScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("JobMaster AI", style = MaterialTheme.typography.headlineMedium)
        Text("Mobile companion for autonomous job applications", style = MaterialTheme.typography.bodyMedium)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Applications today", style = MaterialTheme.typography.titleMedium)
                Text("0", style = MaterialTheme.typography.displaySmall)
            }
        }
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Quality threshold", style = MaterialTheme.typography.titleMedium)
                Text("70", style = MaterialTheme.typography.displaySmall)
            }
        }
        Text("Mode: Hybrid (Manual review for gray-zone jobs)", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun SearchScreen() {
    var keywords by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var searching by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Job Search", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = keywords,
            onValueChange = { keywords = it },
            label = { Text("Keywords") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            singleLine = true
        )
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location / ZIP") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Button(
            onClick = { searching = true },
            modifier = Modifier.padding(top = 16.dp),
            enabled = keywords.isNotBlank()
        ) {
            Text(if (searching) "Searching..." else "Search")
        }
        if (searching) {
            Spacer(Modifier.height(16.dp))
            Text("Searching for \"$keywords\" near \"$location\"...", style = MaterialTheme.typography.bodyMedium)
            Text("Sources: Indeed, LinkedIn, ZipRecruiter, Glassdoor", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(Modifier.height(16.dp))
        Text("Sources: Indeed, LinkedIn, ZipRecruiter, Glassdoor + more coming", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun ResumeScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Resume", style = MaterialTheme.typography.headlineMedium)
        Text("Upload and manage tailored versions", style = MaterialTheme.typography.bodyMedium)
        Button(onClick = {}, modifier = Modifier.padding(top = 16.dp)) {
            Text("Upload PDF Resume")
        }
        Spacer(Modifier.height(16.dp))
        Text("Wheelhouse Guardrail active — no fabricated skills", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun SettingsScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Text("Operating mode, API keys, rate limits, notifications", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(16.dp))
        Text("• Hybrid mode (recommended)", style = MaterialTheme.typography.bodyMedium)
        Text("• Auto quality threshold: 70", style = MaterialTheme.typography.bodyMedium)
        Text("• Notifications: On", style = MaterialTheme.typography.bodyMedium)
    }
}
