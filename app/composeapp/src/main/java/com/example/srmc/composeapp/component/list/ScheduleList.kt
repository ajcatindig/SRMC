package com.example.srmc.composeapp.component.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.srmc.composeapp.component.cards.ScheduleCard
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.model.Schedules

@Composable
fun ScheduleList(data : List<Schedules> , onClick : (Int , String) -> Unit , doctor : Doctor)
{
    LazyColumn(contentPadding = PaddingValues(vertical = 4.dp),
               modifier = Modifier.testTag("scheduleList"))
    {
        items(
            items = data,
            itemContent = { index ->
                ScheduleCard(
                        day_id =  index.day_id!!,
                        start_time =  index.start_time!!,
                        end_time =  index.end_time!!,
                        date_label =  index.date_label!!,
                        date =  index.date!!,
                        slots =  index.slots!!,
                        onBookClick = { onClick(doctor.id!!, index.date!!) })
            },
            key = { Triple(it.id , it.date , it.start_time) }
        )
    }
}