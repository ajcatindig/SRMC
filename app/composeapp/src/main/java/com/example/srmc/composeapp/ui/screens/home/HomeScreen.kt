package com.example.srmc.composeapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.R
import com.example.srmc.composeapp.component.ConnectivityStatus
import com.example.srmc.composeapp.component.anim.LottieAnimation
import com.example.srmc.composeapp.component.list.DoctorList
import com.example.srmc.composeapp.component.scaffold.SRMCScaffold
import com.example.srmc.composeapp.component.scaffold.main.HomeTopBar
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.core.model.Doctor
import com.example.srmc.view.viewmodel.main.DoctorViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
        viewModel : DoctorViewModel,
        onNavigateToDoctorDetail : (Int) -> Unit)
{
    val state by viewModel.collectState()

    HomeContent(
            isLoading =  state.isLoading,
            isConnectivityAvailable = state.isConnectivityAvailable,
            onRefresh = viewModel::getAllDoctors,
            data = state.data,
            onNavigateToDoctorDetail = onNavigateToDoctorDetail)
}

@Composable
fun HomeContent(
        data : List<Doctor>,
        onNavigateToDoctorDetail : (Int) -> Unit,
        isLoading : Boolean,
        isConnectivityAvailable : Boolean?,
        error : String? = null,
        onRefresh : () -> Unit)
{
    SRMCScaffold(
            error = error,
            topAppBar = {
                HomeTopBar()
            },
            content = {
                SwipeRefresh(
                        state = rememberSwipeRefreshState(isLoading) ,
                        onRefresh = onRefresh,
                        swipeEnabled = isConnectivityAvailable == true)
                {
                    Column {
                        if (isConnectivityAvailable != null) {
                            ConnectivityStatus(isConnectivityAvailable)
                        }
                        if (data != null) {
                            DoctorList(data) { index -> onNavigateToDoctorDetail(index.id!!) }
                        }
                        else {
                            Column(modifier = Modifier.fillMaxSize(),
                                   horizontalAlignment = Alignment.CenterHorizontally,
                                   verticalArrangement = Arrangement.Center) 
                            {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically) 
                                {
                                    LottieAnimation(resId = R.raw.no_doctors,
                                                    modifier = Modifier.size(250.dp))
                                }
                                Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp , vertical = 8.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically)
                                {
                                    Text(text = "No doctors found" ,
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