package com.example.srmc.composeapp.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.srmc.composeapp.navigation.RootNavGraph
import com.example.srmc.composeapp.ui.theme.SRMCTheme
import com.example.srmc.core.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.srmc.R
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{

    @Inject
    lateinit var preferenceManager : PreferenceManager
    override fun onCreate(savedInstanceState : Bundle?)
    {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE , WindowManager.LayoutParams.FLAG_SECURE)
        setContent {
            SRMCMain()
        }
        observeUiTheme()
    }

    @Composable
    private fun SRMCMain()
    {
        val darkMode by preferenceManager.uiModeFlow.collectAsState(initial = isSystemInDarkTheme())

        SRMCTheme(darkTheme = darkMode) {
            Surface {
                RootNavGraph(navController = rememberNavController())
            }
        }
    }

    private fun observeUiTheme() {
        lifecycleScope.launchWhenStarted {
            preferenceManager.uiModeFlow.collect { isDarkMode ->
                val mode = when (isDarkMode) {
                    true -> AppCompatDelegate.MODE_NIGHT_YES
                    false -> AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }
}

