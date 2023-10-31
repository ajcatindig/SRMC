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
import com.example.srmc.view.viewmodel.detail.AppointmentDetailViewModel
import com.example.srmc.view.viewmodel.detail.DoctorDetailViewModel
import com.example.srmc.view.viewmodel.detail.ScheduleDetailViewModel
import com.example.srmc.view.viewmodel.form.SchedulesViewModel
import com.example.srmc.view.viewmodel.form.SlotsViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{

    @Inject
    lateinit var preferenceManager : PreferenceManager

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider{

        fun scheduleViewModelFactory() : SchedulesViewModel.Factory
        fun scheduleDetailViewModelFactory() : ScheduleDetailViewModel.Factory
        fun slotsViewModelFactory() : SlotsViewModel.Factory
        fun doctorViewModelFactory() : DoctorDetailViewModel.Factory
        fun appointmentDetailViewModelFactory() : AppointmentDetailViewModel.Factory
    }
    override fun onCreate(savedInstanceState : Bundle?)
    {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
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

