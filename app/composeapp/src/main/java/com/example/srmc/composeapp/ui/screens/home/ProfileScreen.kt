package com.example.srmc.composeapp.ui.screens.home

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.R
import com.example.srmc.composeapp.component.ConnectivityStatus
import com.example.srmc.composeapp.component.anim.LottieAnimation
import com.example.srmc.composeapp.component.cards.ProfileCard
import com.example.srmc.composeapp.component.dialog.ConfirmationDialog
import com.example.srmc.composeapp.component.scaffold.SRMCScaffold
import com.example.srmc.composeapp.component.scaffold.main.ProfileTopBar
import com.example.srmc.composeapp.component.scaffold.main.TransactionTopBar
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.IntentUtils
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.core.model.User
import com.example.srmc.view.viewmodel.main.ProfileViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ProfileScreen(
        viewModel : ProfileViewModel,
        onNavigateToLogin : () -> Unit,
        onAboutAppClick : () -> Unit)
{
    val state by viewModel.collectState()

    var showLogoutConfirmation by remember { mutableStateOf(false) }

    ProfileContent(
            isLoading =  state.isLoading,
            isConnectivityAvailable = state.isConnectivityAvailable,
            onRefresh = viewModel::getCurrentUser,
            onAboutAppClick = onAboutAppClick,
            onLogoutClick = { showLogoutConfirmation = true },
            data = state.data)

    LogoutConfirmation(
            show = showLogoutConfirmation ,
            onConfirm = viewModel::logout,
            onDismiss = { showLogoutConfirmation = false })

    val isUserLoggedIn = state.isUserLoggedIn
    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn == false) {
            onNavigateToLogin()
        }
    }
}

@Composable
fun ProfileContent(
        isLoading : Boolean,
        isConnectivityAvailable : Boolean?,
        data : User,
        error : String? = null,
        onRefresh : () -> Unit,
        onAboutAppClick : () -> Unit,
        onLogoutClick : () -> Unit)
{
    val context = LocalContext.current
    val changePassLink = "https://srmc-front.mcroad.com/change-password"
    val manageProfileLink = "https://srmc-front.mcroad.com/edit-profile"


    SRMCScaffold(
            error = error,
            topAppBar = {
                ProfileTopBar()
            },
            content = {
                SwipeRefresh(
                        state = rememberSwipeRefreshState(isLoading) ,
                        onRefresh = onRefresh,
                        swipeEnabled = isConnectivityAvailable == true)
                {
                    Column(modifier = Modifier.fillMaxSize()
                            .background(MaterialTheme.colors.surface))
                    {
                        if (isConnectivityAvailable != null) {
                            ConnectivityStatus(isConnectivityAvailable)
                        }
                        ProfileCard(
                                data =  data,
                                onLogoutClick = { onLogoutClick() } ,
                                onAboutAppClick = { onAboutAppClick() } ,
                                onChangePasswordClick = { IntentUtils.launchBrowser(context, changePassLink) },
                                onManageProfileClick = { IntentUtils.launchBrowser(context, manageProfileLink) })
                    }
                }
            }
    )
}

@Composable
fun LogoutConfirmation(show: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    if (show) {
        ConfirmationDialog(
                title = "Logout?",
                message = "Are you sure you want to logout?",
                onConfirmedYes = onConfirm,
                onConfirmedNo = onDismiss,
                onDismissed = onDismiss)
    }
}