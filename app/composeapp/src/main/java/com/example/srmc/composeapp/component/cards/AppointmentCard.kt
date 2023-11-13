package com.example.srmc.composeapp.component.cards

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.ui.theme.buttonGreen
import com.example.srmc.composeapp.ui.theme.darkOrange
import com.example.srmc.composeapp.ui.theme.darkRed
import com.example.srmc.composeapp.ui.theme.darkYellow
import com.example.srmc.composeapp.ui.theme.lightBlue
import com.example.srmc.composeapp.ui.theme.surfaceNight
import com.example.srmc.composeapp.ui.theme.typography
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AppointmentCard(
        type : String,
        start_time : String,
        end_time : String,
        check_in : String?,
        check_out : String?,
        accepted_at : String?,
        cancelled_at : String?,
        rescheduled_at : String?,
        verified_at : String?,
        payment_link : String?,
        doctor_name : String,
        follow_up_start_time : String?,
        follow_up_end_time : String?,
        followed_up_at : String?,
        scheduled_at : String,
        created_at : String,
        onAppointmentClick : () -> Unit)
{
    val timeFormatter24 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

// Parse the time strings from the API
    val startTime = if (!start_time.isNullOrEmpty()) timeFormatter24.parse(start_time) else null
    val endTime = if (!end_time.isNullOrEmpty()) timeFormatter24.parse(end_time) else null
    val followUpStartTime = follow_up_start_time?.let { if (!it.isNullOrEmpty()) timeFormatter24.parse(it) else null }
    val followUpEndTime = follow_up_end_time?.let { if (!it.isNullOrEmpty()) timeFormatter24.parse(it) else null }

// Check if parsing was successful before formatting
    val timeFormatter12 = SimpleDateFormat("h:mm a", Locale.getDefault())
    val start = startTime?.let { timeFormatter12.format(it) }
    val end = endTime?.let { timeFormatter12.format(it) }
    val follow_start = followUpStartTime?.let { timeFormatter12.format(it) }
    val follow_end = followUpEndTime?.let { timeFormatter12.format(it) }
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
                .padding(vertical = 4.dp , horizontal = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .size(250.dp)
                .clickable { onAppointmentClick() },
        elevation = 2.dp)
    {
        Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center)
        {
            //Date
            Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) 
                {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start)
                    {
                        Text(text = if (rescheduled_at?.isNotEmpty()!!) { "$rescheduled_at" }
                             else if (followed_up_at?.isNotEmpty()!!) { "$followed_up_at" }
                             else { scheduled_at } ,
                             style = typography.h5 ,
                             textAlign = TextAlign.Start ,
                             fontSize = 20.sp ,
                             maxLines = 1 ,
                             overflow = TextOverflow.Ellipsis)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End)
                    {
                        AppointmentStatusChip(status =
                            if (accepted_at?.isNotEmpty()!! && cancelled_at?.isEmpty()!! && check_out?.isEmpty()!!)
                            {
                                "upcoming"
                            } else if (cancelled_at?.isNotEmpty()!! || accepted_at.isNotEmpty())
                            {
                                "cancelled"
                            } else if (rescheduled_at?.isNotEmpty()!! || accepted_at.isNotEmpty() )
                            {
                                "rescheduled"
                            } else if (followed_up_at?.isNotEmpty()!! || accepted_at.isNotEmpty())
                            {
                                "followup"
                            } else if (check_out?.isNotEmpty()!!)
                            {
                                "past"
                            } else {
                                "pending"
                            })
                    }
                }
            }
            //Doctor Name
            Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start)
                {
                    Text(text = "Dr. $doctor_name" ,
                         style = typography.subtitle1 ,
                         textAlign = TextAlign.Start ,
                         fontSize = 18.sp ,
                         maxLines = 1 ,
                         overflow = TextOverflow.Ellipsis)
                }
            }
            //Time
            Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start)
                {
                    Text(text = if (follow_up_start_time?.isNotEmpty()!! && follow_up_end_time?.isNotEmpty()!!) {
                            "Time: $follow_start - $follow_end"
                    } else {
                           "Time: $start - $end"
                    },
                         style = typography.subtitle1 ,
                         textAlign = TextAlign.Start ,
                         fontSize = 18.sp ,
                         maxLines = 2 ,
                         overflow = TextOverflow.Ellipsis)
                }
            }
            //Consultation Type
            Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text = "Consultation Type: $type" ,
                         style = typography.subtitle1 ,
                         textAlign = TextAlign.Start ,
                         fontSize = 18.sp ,
                         maxLines = 2 ,
                         overflow = TextOverflow.Ellipsis)
                }
            }
            //Payment Status
            Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start)
                {
                    Text(text = "Payment Status: ",
                         style = typography.subtitle1,
                         textAlign = TextAlign.Start,
                         fontSize = 18.sp,
                         maxLines = 2,
                         overflow = TextOverflow.Ellipsis,
                         modifier = Modifier.padding(end = 8.dp))
                    PaymentStatusChip(status = if(verified_at?.isNotEmpty()!!) "yes" else "pending")
                }
            }
        }
    }
}

@Composable
fun PaymentStatusChip(status : String?)
{
    val isPaid = buttonGreen
    val isPending = darkOrange

    val pending = "PENDING"
    val paid = "PAID"

    Card(modifier = Modifier
            .width(100.dp)
            .height(30.dp),
         shape = RoundedCornerShape(25.dp),
         backgroundColor = when(status) {
             "yes" -> isPaid
             else -> isPending
         },
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
                    },
                     style = typography.h5,
                     fontSize = 14.sp,
                     textAlign = TextAlign.Center,
                     color = Color.White,
                     maxLines = 1)
            }
        }
    }
}

@Composable
fun AppointmentStatusChip(status : String?)
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
    val past = "PAST"

    Card(modifier = Modifier
            .width(110.dp)
            .height(30.dp),
         shape = RoundedCornerShape(25.dp),
         backgroundColor = when(status) {
             "upcoming" -> isUpcoming
             "pending" -> isPending
             "cancelled" -> isCancelled
             "rescheduled" -> isRescheduled
             "followup" -> isFollowUp
             "past" -> isPast
             else -> isPending
         },
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
                },
                     style = typography.h5,
                     fontSize = 14.sp,
                     textAlign = TextAlign.Center,
                     color = Color.White,
                     maxLines = 1)
            }
        }
    }

}