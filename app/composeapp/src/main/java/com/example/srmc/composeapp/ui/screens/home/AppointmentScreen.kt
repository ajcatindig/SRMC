package com.example.srmc.composeapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.R
import com.example.srmc.composeapp.component.ConnectivityStatus
import com.example.srmc.composeapp.component.anim.LottieAnimation
import com.example.srmc.composeapp.component.list.AppointmentList
import com.example.srmc.composeapp.component.scaffold.SRMCScaffold
import com.example.srmc.composeapp.component.scaffold.main.AppointmentTopBar
import com.example.srmc.composeapp.component.scaffold.main.HomeTopBar
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.core.model.Appointments
import com.example.srmc.view.viewmodel.main.AppointmentViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun AppointmentScreen(
        viewModel : AppointmentViewModel,
        onNavigateToAppointmentDetail : (Int) -> Unit)
{
    val state by viewModel.collectState()

    AppointmentContent(
            isLoading =  state.isLoading,
            isConnectivityAvailable = state.isConnectivityAvailable,
            onRefresh = viewModel::getAllAppointments,
            data = state.data,
            onNavigateToAppointmentDetail = onNavigateToAppointmentDetail)
}

@Composable
fun AppointmentContent(
        isLoading : Boolean,
        isConnectivityAvailable : Boolean?,
        data : List<Appointments>,
        error : String? = null,
        onRefresh : () -> Unit,
        onNavigateToAppointmentDetail : (Int) -> Unit)
{
    SRMCScaffold(
        error = error,
        topAppBar = {
            AppointmentTopBar()
        },
        content = {
            SwipeRefresh(
                    state = rememberSwipeRefreshState(isLoading) ,
                    onRefresh = onRefresh,
                    swipeEnabled = true)
            {
                Column {
                    if (isConnectivityAvailable != null) {
                        ConnectivityStatus(isConnectivityAvailable)
                    }
                    if (data != null) {
                        AppointmentList(data) { index -> onNavigateToAppointmentDetail(index.id!!) }
                    } else {
                        Column(modifier = Modifier.fillMaxSize() ,
                               horizontalAlignment = Alignment.CenterHorizontally ,
                               verticalArrangement = Arrangement.Center)
                        {
                            Row(modifier = Modifier.fillMaxWidth() ,
                                horizontalArrangement = Arrangement.Center ,
                                verticalAlignment = Alignment.CenterVertically)
                            {
                                LottieAnimation(resId = R.raw.no_appointments ,
                                                modifier = Modifier.size(250.dp))
                            }
                            Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp , vertical = 8.dp) ,
                                horizontalArrangement = Arrangement.Center ,
                                verticalAlignment = Alignment.CenterVertically)
                            {
                                Text(text = "You don't have any appointments" ,
                                     style = typography.h5 ,
                                     fontSize = 18.sp ,
                                     textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }
        }
    )
}