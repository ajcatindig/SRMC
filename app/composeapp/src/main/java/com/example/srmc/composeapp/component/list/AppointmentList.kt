package com.example.srmc.composeapp.component.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.srmc.composeapp.component.cards.AppointmentCard
import com.example.srmc.core.model.Appointments

@Composable
fun AppointmentList(data : List<Appointments?>, onClick : (Appointments) -> Unit)
{
    LazyColumn(contentPadding = PaddingValues(vertical = 4.dp),
               modifier = Modifier.testTag("appointmentList"))
    {
        items(
            items = data,
            itemContent = { index ->
                AppointmentCard(
                        type =  index?.type.orEmpty() ,
                        start_time =  index?.start_time.orEmpty()  ,
                        end_time =  index?.end_time.orEmpty()  ,
                        check_in = index?.check_in.orEmpty(),
                        check_out = index?.check_out.orEmpty(),
                        accepted_at = index?.accepted_at.orEmpty(),
                        cancelled_at = index?.cancelled_at.orEmpty(),
                        rescheduled_at = index?.rescheduled_at.orEmpty(),
                        verified_at = index?.verified_at.orEmpty(),
                        payment_link = index?.payment_link.orEmpty(),
                        doctor_name = index?.doctor_name.orEmpty(),
                        follow_up_start_time = index?.follow_up_start_time.orEmpty(),
                        follow_up_end_time = index?.follow_up_end_time.orEmpty(),
                        followed_up_at = index?.followed_up_at.orEmpty(),
                        scheduled_at = index?.scheduled_at.orEmpty(),
                        created_at = index?.created_at.orEmpty(),
                        onAppointmentClick = { onClick(index!!) })
            },
            key = { Triple(it?.id, it?.doctor_id, it?.scheduled_at) }
        )
    }
}