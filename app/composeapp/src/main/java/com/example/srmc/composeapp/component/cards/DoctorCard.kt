package com.example.srmc.composeapp.component.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.SRMCPreview
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DoctorCard(
        imageUrl : String?,
        doctorName : String,
        doctorTitle : String?,
        onDoctorClick : () -> Unit)
{
    Card(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier
                    .padding(8.dp)
                    .height(270.dp)
                    .clickable { onDoctorClick() },
            elevation = 2.dp)
    {
        Column(
                modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
              )
        {
            GlideImage(
                    imageModel =  imageUrl  ,
                    modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape) ,
                    loading = {
                        Box(modifier = Modifier.matchParentSize()) {
                            CircularProgressIndicator(
                                    modifier = Modifier
                                            .align(Alignment.Center),
                                    color = Color(0xff15C3DD)
                            )
                        }
                    } ,
                    failure = {
                        val substring = doctorName.substring(0,1).toUpperCase()
                        Box(modifier = Modifier
                                .matchParentSize()
                                .background(Color(0xff15C3DD)))
                        {
                            Text(
                                    text = substring,
                                    modifier = Modifier.align(Alignment.Center),
                                    style = MaterialTheme.typography.h4,
                                    color = Color.White)
                        }
                    }
            )
            Row(
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
               )
            {
                Text(text = "Dr. $doctorName" ,
                     style = typography.h4,
                     fontSize = 16.sp,
                     overflow = TextOverflow.Ellipsis,
                     maxLines = 2,
                     textAlign = TextAlign.Center)
            }
            Row(
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
               )
            {
                Text(text = doctorTitle ?: "General Medicine" ,
                     style = typography.subtitle2,
                     fontSize = 14.sp,
                     maxLines = 2,
                     overflow = TextOverflow.Ellipsis,
                     color = Color.DarkGray,
                     textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview
@Composable
fun PreviewDoctorCard() = SRMCPreview {
    DoctorCard(
            imageUrl = "https://image.civitai.com/xG1nkqKTMzGDvpLrqFT7WA/4a287732-d033-48b6-c0a0-d3553c8bff00/width=1200/4a287732-d033-48b6-c0a0-d3553c8bff00.jpeg",
            doctorName =  "Johnny Sins",
            doctorTitle = "Neurosurgeon",
            onDoctorClick = {})
}