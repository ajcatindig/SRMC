package com.example.srmc.composeapp.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.example.srmc.composeapp.component.ConnectivityStatus
import com.example.srmc.composeapp.component.cards.AppointmentStatusChip
import com.example.srmc.composeapp.component.dialog.ConfirmationDialog
import com.example.srmc.composeapp.component.dialog.FailureDialog
import com.example.srmc.composeapp.component.dialog.LoaderDialog
import com.example.srmc.composeapp.component.dialog.SuccessDialog
import com.example.srmc.composeapp.component.scaffold.SRMCScaffold
import com.example.srmc.composeapp.component.scaffold.detail.AppointmentDetailTopBar
import com.example.srmc.composeapp.ui.theme.buttonGreen
import com.example.srmc.composeapp.ui.theme.buttonRed
import com.example.srmc.composeapp.ui.theme.darkOrange
import com.example.srmc.composeapp.ui.theme.darkRed
import com.example.srmc.composeapp.ui.theme.darkYellow
import com.example.srmc.composeapp.ui.theme.lightBlue
import com.example.srmc.composeapp.ui.theme.surfaceNight
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.IntentUtils
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.core.model.Appointments
import com.example.srmc.view.viewmodel.detail.AppointmentDetailViewModel
import com.example.srmc.view.viewmodel.form.CancelViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.glide.GlideImage
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AppointmentDetailScreen(
        onNavigateUp : () -> Unit,
        viewModel : AppointmentDetailViewModel,
        cancelViewModel : CancelViewModel,
        onNavigateToForm : (Int, Int) -> Unit)
{
    val state by viewModel.collectState()
    val state2 by cancelViewModel.collectState()

    var showConfirmation by remember { mutableStateOf(false) }

    AppointmentContent(
            data =  state.data,
            isLoading =  state.isLoading,
            isConnectivityAvailable =  state.isConnectivityAvailable,
            error =  state.error,
            onNavigateUp = onNavigateUp ,
            onRefresh = viewModel::loadAppointment ,
            onNavigateToForm = onNavigateToForm,
            onCancelClick = { showConfirmation = true })

    CancelConfirmation(show = showConfirmation ,
                       onConfirm =  cancelViewModel::cancelAppointment,
                       onDismiss = { showConfirmation = false },
                       appointment = state.data,
                       isSuccess = state2.isSuccess,
                       error = state2.error,
                       isLoading = state2.isLoading)
}

@Composable
fun AppointmentContent(
        data : Appointments,
        isLoading : Boolean,
        isConnectivityAvailable : Boolean?,
        error : String?,
        onNavigateUp : () -> Unit,
        onRefresh : () -> Unit,
        onNavigateToForm : (Int, Int) -> Unit,
        onCancelClick : () -> Unit)
{
    val context = LocalContext.current
    val paymentUrl = data.payment_link.orEmpty()
    val paymentEnabled by derivedStateOf { data.verified_at == null && data.payment_link != null && data.cancelled_at == null && data.accepted_at != null }
    val reschedEnabled by derivedStateOf { data.cancelled_at == null && data.check_in == null && data.check_out == null}
    val cancelEnabled by derivedStateOf { data.accepted_at != null && data.check_in == null && data.check_out == null && data.cancelled_at == null }
    val conditionMet by derivedStateOf { data.meeting_link != null }

    val timeFormatter24 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

// Parse the time strings from the API
    val startTime = if (!data.start_time.isNullOrEmpty()) timeFormatter24.parse(data.start_time) else null
    val endTime = if (!data.end_time.isNullOrEmpty()) timeFormatter24.parse(data.end_time) else null
    val followUpStartTime = data.follow_up_start_time?.let { if (!it.isNullOrEmpty()) timeFormatter24.parse(it) else null }
    val followUpEndTime = data.follow_up_end_time?.let { if (!it.isNullOrEmpty()) timeFormatter24.parse(it) else null }

// Check if parsing was successful before formatting
    val timeFormatter12 = SimpleDateFormat("h:mm a", Locale.getDefault())
    val start = startTime?.let { timeFormatter12.format(it) }
    val end = endTime?.let { timeFormatter12.format(it) }
    val follow_start = followUpStartTime?.let { timeFormatter12.format(it) }
    val follow_end = followUpEndTime?.let { timeFormatter12.format(it) }


    val baseLink = "https://srmc.mcbroad.com/storage/"
    val externalLinkSymbol = "\uD83D\uDD17"

    SRMCScaffold(
        error = error,
        topAppBar = {
            AppointmentDetailTopBar(
                onNavigateUp = onNavigateUp
            )
        },
        content = {
            SwipeRefresh(
                state =  rememberSwipeRefreshState(isLoading),
                onRefresh = onRefresh,
                swipeEnabled = true)
            {
                Column {
                    if (isConnectivityAvailable != null) {
                        ConnectivityStatus(isConnectivityAvailable)
                    }
                    Surface(modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.surface))
                    {
                        LazyColumn(modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp))
                        {
                            item {
                                Column(horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.Center,
                                       modifier = Modifier.padding(horizontal = 24.dp, vertical = 40.dp))
                                {
                                    Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically)
                                    {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally)
                                        {
                                            Row(modifier = Modifier.padding(16.dp),
                                                horizontalArrangement = Arrangement.Center)
                                            {
                                                GlideImage(
                                                    imageModel = baseLink + data.doctor.profile_photo_path,
                                                    modifier = Modifier
                                                            .size(150.dp)
                                                            .clip(CircleShape),
                                                    loading = {
                                                        Box(modifier = Modifier.matchParentSize())
                                                        {
                                                            CircularProgressIndicator(
                                                                modifier = Modifier.align(Alignment.Center),
                                                                color = Color(0xff15C3DD))
                                                        }
                                                    },
                                                    failure = {
                                                        val substring = data.doctor.name?.substring(0, 1)?.toUpperCase()
                                                        Box(modifier = Modifier
                                                                .matchParentSize()
                                                                .background(color = Color(0xff15C3DD)))
                                                        {
                                                            Text(text = substring.orEmpty(),
                                                                 modifier = Modifier.align(Alignment.Center),
                                                                 style = MaterialTheme.typography.h4,
                                                                 fontSize = 40.sp,
                                                                 color = Color.White)
                                                        }
                                                    }
                                                )
                                            }
                                            Row(verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center,
                                                modifier = Modifier.padding(bottom = 4.dp))
                                            {
                                                Text(text = "Dr. ${data.doctor.name}",
                                                     style = typography.h5,
                                                     fontSize = 18.sp)
                                            }
                                            Row(verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center,
                                                modifier = Modifier.padding(bottom = 8.dp))
                                            {
                                                Text(text = "${data.doctor.title}",
                                                     style = typography.subtitle2,
                                                     fontSize = 16.sp)
                                            }
                                        }
                                    }
                                    Row {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally)
                                        {
                                            Column(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 16.dp),
                                                   horizontalAlignment = Alignment.CenterHorizontally,
                                                   verticalArrangement = Arrangement.Center)
                                            {
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = "Appointment & Payment Status",
                                                         style = typography.h5,
                                                         fontSize = 18.sp)
                                                }
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp , start = 50.dp , end = 50.dp),
                                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    AppointmentStatus(status =
                                                        if (data.accepted_at != null && data.cancelled_at == null && data.check_out == null)
                                                        {
                                                            "upcoming"
                                                        } else if (data.accepted_at != null && data.cancelled_at != null)
                                                        {
                                                            "cancelled"
                                                        } else if (data.rescheduled_at != null && data.accepted_at != null)
                                                        {
                                                            "rescheduled"
                                                        } else if (data.followed_up_at != null && data.accepted_at != null)
                                                        {
                                                            "followup"
                                                        } else if (data.check_in != null && data.check_out != null)
                                                        {
                                                            "past"
                                                        } else {
                                                            "pending"
                                                        })
                                                    PaymentStatus(status = if (data.verified_at != null) "yes" else "pending")
                                                }
                                            }
                                            Column(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 16.dp , top = 24.dp))
                                            {
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = "Doctor's Contact Information",
                                                         style = typography.h5,
                                                         fontSize = 18.sp)
                                                }
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = "${data.doctor.email}",
                                                         style = typography.caption,
                                                         fontSize = 16.sp)
                                                }
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = "${data.doctor.contact_number}",
                                                         style = typography.caption,
                                                         fontSize = 16.sp)
                                                }
                                            }
                                            Column(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 16.dp , top = 16.dp),
                                                   horizontalAlignment = Alignment.CenterHorizontally,
                                                   verticalArrangement = Arrangement.Center)
                                            {
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = "Appointment Details",
                                                         style = typography.h5,
                                                         fontSize = 18.sp)
                                                }
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = if (data.rescheduled_at != null) { "Date: ${data.rescheduled_at}" }
                                                         else if (data.followed_up_at != null) { "Date: ${data.followed_up_at}" }
                                                         else { "Date: ${data.scheduled_at}" },
                                                         style = typography.caption,
                                                         fontSize = 16.sp)
                                                }
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = if (data.follow_up_start_time != null && data.follow_up_end_time != null) {
                                                        "Time: $follow_start"
                                                    } else {
                                                        "Time: $start"
                                                    },
                                                         style = typography.caption,
                                                         fontSize = 16.sp)
                                                }
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = "Consultation Type: ${data.type}",
                                                         style = typography.caption,
                                                         fontSize = 16.sp)
                                                }
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .run {
                                                             if (conditionMet) {
                                                                 wrapContentHeight()
                                                                 alpha(1.0f)
                                                             } else {
                                                                 height(0.dp)
                                                                 alpha(0.0f)
                                                             }
                                                        },
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    )
                                                {
                                                    Text(text = buildAnnotatedString {
                                                        append("Join Meeting Now $externalLinkSymbol")
                                                        addStyle(SpanStyle(color = Color(0xff15C3DD)) , start = 0 , this.length)
                                                        toAnnotatedString()
                                                    },
                                                         style = typography.caption,
                                                         fontSize = 16.sp,
                                                         modifier = Modifier.clickable { data.meeting_link?.let { it1 -> IntentUtils.launchBrowser(context , it1) } })
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            item {
                                Column(modifier = Modifier
                                        .fillMaxSize()
                                        .padding(start = 16.dp, end = 16.dp, bottom = 40.dp),
                                       verticalArrangement = Arrangement.Bottom,
                                       horizontalAlignment = Alignment.CenterHorizontally)
                                {
                                    Row(modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically)
                                    {
                                        Row(horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically)
                                        {
                                            Button(
                                                onClick = { onNavigateToForm(data.id!!, data.doctor.id!!) } ,
                                                modifier = Modifier
                                                        .height(45.dp) ,
                                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff15C3DD)) ,
                                                shape = RoundedCornerShape(25.dp),
                                                enabled = reschedEnabled)
                                            {
                                                Text(text = "Reschedule" , color = Color.White , style = typography.h6, fontSize = 16.sp, textAlign = TextAlign.Center)
                                            }
                                        }
                                        Row(horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically)
                                        {
                                            Button(
                                                    onClick = { IntentUtils.launchBrowser(context, paymentUrl) } ,
                                                    modifier = Modifier
                                                            .height(45.dp) ,
                                                    colors = ButtonDefaults.buttonColors(backgroundColor = buttonGreen) ,
                                                    shape = RoundedCornerShape(25.dp),
                                                    enabled = paymentEnabled)
                                            {
                                                Text(text = "Pay" , color = Color.White , style = typography.h6, fontSize = 16.sp, textAlign = TextAlign.Center)
                                            }
                                        }
                                        Row(horizontalArrangement = Arrangement.End,
                                            verticalAlignment = Alignment.CenterVertically)
                                        {
                                            Button(
                                                    onClick = { onCancelClick() } ,
                                                    modifier = Modifier
                                                            .height(45.dp) ,
                                                    colors = ButtonDefaults.buttonColors(backgroundColor = buttonRed) ,
                                                    shape = RoundedCornerShape(25.dp),
                                                    enabled = cancelEnabled)
                                            {
                                                Text(text = "Cancel" , color = Color.White , style = typography.h6, fontSize = 16.sp, textAlign = TextAlign.Center)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PaymentStatus(status : String?)
{
    val isPaid = buttonGreen
    val isPending = surfaceNight

    val pending = "UNPAID"
    val paid = "PAID"

    Card(modifier = Modifier
            .width(105.dp)
            .height(35.dp) ,
         shape = RoundedCornerShape(25.dp) ,
         backgroundColor = when(status) {
             "yes" -> isPaid
             else -> isPending
         } ,
         elevation = 0.dp)
    {
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp , vertical = 4.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center)
        {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = when(status) {
                    "yes" -> paid
                    else -> pending
                } ,
                     style = typography.h5 ,
                     fontSize = 14.sp ,
                     textAlign = TextAlign.Center ,
                     color = Color.White ,
                     maxLines = 1)
            }
        }
    }
}

@Composable
fun AppointmentStatus(status : String?)
{
    val isUpcoming = buttonGreen
    val isCancelled = darkRed
    val isPending = darkOrange
    val isRescheduled = darkYellow
    val isFollowUp = lightBlue
    val isPast = surfaceNight

    val upcoming = "UPCOMING"
    val pending = "PENDING"
    val cancelled = "CANCELLED"
    val rescheduled = "RESCHEDULED"
    val followUp = "FOLLOW-UP"
    val past = "COMPLETED"

    Card(modifier = Modifier
            .width(105.dp)
            .height(35.dp) ,
         shape = RoundedCornerShape(25.dp) ,
         backgroundColor = when(status) {
             "upcoming" -> isUpcoming
             "pending" -> isPending
             "cancelled" -> isCancelled
             "rescheduled" -> isRescheduled
             "followup" -> isFollowUp
             "past" -> isPast
             else -> isPending
         } ,
         elevation = 0.dp)
    {
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp , vertical = 4.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center)
        {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = when(status) {
                    "upcoming" -> upcoming
                    "pending" -> pending
                    "cancelled" -> cancelled
                    "rescheduled" -> rescheduled
                    "followup" -> followUp
                    "past" -> past
                    else -> pending
                } ,
                     style = typography.h5 ,
                     fontSize = 14.sp ,
                     textAlign = TextAlign.Center ,
                     color = Color.White ,
                     maxLines = 1)
            }
        }
    }

}

@Composable
fun CancelConfirmation(
        show : Boolean,
        onConfirm : (Int) -> Unit,
        onDismiss : () -> Unit,
        appointment : Appointments,
        isSuccess : String?,
        error : String?,
        isLoading : Boolean)
{
    if (show)
    {
        ConfirmationDialog(
                title =  "Cancel Appointment?",
                message =  "You cannot undo this operation. Do you wish to continue?",
                onConfirmedYes = {  onConfirm(appointment.id!!) }  ,
                onConfirmedNo = onDismiss,
                onDismissed = onDismiss)
    }

    if (isLoading) {
        LoaderDialog()
    }
    if (isSuccess != null) {
        SuccessDialog(isSuccess)
    }
    if(error != null) {
        FailureDialog(error)
    }
}