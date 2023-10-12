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
import com.example.srmc.composeapp.component.scaffold.SRMCScaffold
import com.example.srmc.composeapp.component.scaffold.main.ProfileTopBar
import com.example.srmc.composeapp.component.scaffold.main.TransactionTopBar
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.view.viewmodel.main.ProfileViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ProfileScreen(viewModel : ProfileViewModel)
{
    val state by viewModel.collectState()

    ProfileContent(
            isLoading =  state.isLoading,
            isConnectivityAvailable = state.isConnectivityAvailable,
            onRefresh = {})
}

@Composable
fun ProfileContent(
        isLoading : Boolean,
        isConnectivityAvailable : Boolean?,
        error : String? = null,
        onRefresh : () -> Unit)
{
    SRMCScaffold(
            error = error,
            topAppBar = {
                ProfileTopBar()
            },
            content = {
                SwipeRefresh(
                        state = rememberSwipeRefreshState(isLoading) ,
                        onRefresh = onRefresh,
                        swipeEnabled = isConnectivityAvailable === true)
                {
                    Column {
                        if (isConnectivityAvailable != null) {
                            ConnectivityStatus(isConnectivityAvailable)
                        }
                        else {
                            Column(modifier = Modifier.fillMaxSize() ,
                                   horizontalAlignment = Alignment.CenterHorizontally ,
                                   verticalArrangement = Arrangement.Center)
                            {
                                Row(modifier = Modifier.fillMaxWidth() ,
                                    horizontalArrangement = Arrangement.Center ,
                                    verticalAlignment = Alignment.CenterVertically)
                                {
                                    LottieAnimation(resId = R.raw.error_404,
                                                    modifier = Modifier.size(250.dp))
                                }
                                Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp , vertical = 8.dp) ,
                                    horizontalArrangement = Arrangement.Center ,
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