package com.example.srmc.composeapp.component.cards

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.ui.theme.typography

@Composable
fun ScheduleCard(
        day_id : String,
        start_time : String,
        end_time : String,
        date_label : String,
        date : String,
        slots : Int,
        onBookClick : (String) -> Unit)
{
    val isEnabled by derivedStateOf { slots != 0 && slots != null }

    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface ,
        modifier = Modifier
                .padding(vertical = 4.dp , horizontal = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .size(200.dp)
                .clickable(enabled = isEnabled, onClick = { onBookClick(date) }),
        elevation = 2.dp)
    {
        Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally)
        {
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
                        Text(text = "Day ${day_id.orEmpty()}",
                             style = typography.h5,
                             textAlign = TextAlign.Start,
                             fontSize = 20.sp,
                             maxLines = 1,
                             overflow = TextOverflow.Ellipsis)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End)
                    {
                        StatusChip(slots = slots)
                    }
                }
            }
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
                    Text(text = "Date: $date_label" ,
                         style = typography.h5 ,
                         textAlign = TextAlign.Start ,
                         fontSize = 18.sp ,
                         maxLines = 2 ,
                         overflow = TextOverflow.Ellipsis)
                }
            }
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
                    Text(text = "Time: ${start_time.orEmpty()} - ${end_time.orEmpty()}" ,
                         style = typography.h5 ,
                         textAlign = TextAlign.Start ,
                         fontSize = 18.sp ,
                         maxLines = 2 ,
                         overflow = TextOverflow.Ellipsis)
                }
            }
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
                    Text(text = "Slots Left: ${slots}}" ,
                         style = typography.h5 ,
                         textAlign = TextAlign.Start ,
                         fontSize = 18.sp ,
                         maxLines = 2 ,
                         overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

@Composable
fun StatusChip(slots : Int)
{
    val isAvailable = Color.Green
    val isUnavailable = Color.Red

    Card(modifier = Modifier
            .width(30.dp)
            .height(30.dp)
            .clip(CircleShape),
         backgroundColor = if (slots != 0 || slots != null) isAvailable else isUnavailable,
         elevation = 0.dp)
    {

    }
}