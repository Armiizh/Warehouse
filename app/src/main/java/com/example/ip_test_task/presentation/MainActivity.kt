package com.example.ip_test_task.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ip_test_task.presentation.screen.MainScreen
import com.example.ip_test_task.ui.theme.IptesttaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IptesttaskTheme {
                MainScreen()
            }
        }
    }
}