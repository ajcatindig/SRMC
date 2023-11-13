package com.example.srmc.composeapp.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.component.ConnectivityStatus
import com.example.srmc.composeapp.component.scaffold.SRMCScaffold
import com.example.srmc.composeapp.component.scaffold.detail.DoctorDetailTopBar
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.core.model.Doctor
import com.example.srmc.view.viewmodel.detail.DoctorDetailViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DoctorDetailScreen(
        onNavigateUp : () -> Unit,
        viewModel : DoctorDetailViewModel,
        onNavigateToForm : (Int) -> Unit)
{
    val state by viewModel.collectState()

    DoctorContent(
            data =  state.data,
            isLoading =  state.isLoading,
            isConnectivityAvailable =  state.isConnectivityAvailable,
            error =  state.error,
            onNavigateUp = onNavigateUp ,
            onRefresh = viewModel::loadDoctor ,
            onNavigateToForm = onNavigateToForm)
}

@Composable
fun DoctorContent(
        data : Doctor,
        isLoading : Boolean,
        isConnectivityAvailable : Boolean?,
        error : String?,
        onNavigateUp : () -> Unit,
        onRefresh : () -> Unit,
        onNavigateToForm : (Int) -> Unit)
{
    val baseLink = "https://srmc.mcbroad.com/storage/"

    SRMCScaffold(
        error = error,
        topAppBar = {
            DoctorDetailTopBar(
                onNavigateUp = onNavigateUp
            )
        },
        content = {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isLoading),
                onRefresh = onRefresh,
                swipeEnabled = isConnectivityAvailable == true)
            {
                Column {
                    if (isConnectivityAvailable != null) {
                        ConnectivityStatus(isConnectivityAvailable)
                    }
                    Surface(modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.surface))
                    {
                        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp))
                        {
                            item {
                                Column(horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.Center,
                                       modifier = Modifier.padding(horizontal = 24.dp, vertical = 40.dp))
                                {
                                    Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 30.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically)
                                    {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally)
                                        {
                                            Row(modifier = Modifier.padding(16.dp),
                                                horizontalArrangement = Arrangement.Center)
                                            {
                                                GlideImage(
                                                    imageModel = baseLink + data.profile_photo_path ,
                                                    modifier = Modifier
                                                            .size(150.dp)
                                                            .clip(CircleShape) ,
                                                    loading = {
                                                        Box(modifier = Modifier.matchParentSize())
                                                        {
                                                            CircularProgressIndicator(
                                                                    modifier = Modifier
                                                                            .align(Alignment.Center) ,
                                                                    color = Color(0xff15C3DD))
                                                        }
                                                    } ,
                                                    failure = {
                                                        val substring = data.name?.substring(0 , 1)?.toUpperCase()
                                                        Box(modifier = Modifier
                                                                .matchParentSize()
                                                                .background(color = Color(0xff15C3DD)))
                                                        {
                                                            Text(text = substring.orEmpty() ,
                                                                 modifier = Modifier.align(Alignment.Center) ,
                                                                 style = MaterialTheme.typography.h4 ,
                                                                 fontSize = 40.sp ,
                                                                 color = Color.White)
                                                        }
                                                    }
                                                )
                                            }
                                            Row(verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center,
                                                modifier = Modifier.padding(bottom = 8.dp))
                                            {
                                                Text(text = "Dr. ${data.name.orEmpty()}",
                                                     style = typography.h5,
                                                     fontSize = 18.sp)
                                            }
                                            Row(verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center,
                                                modifier = Modifier.padding(bottom = 8.dp))
                                            {
                                                Text(text = data.title ?: "General Medicine",
                                                     style = typography.subtitle1,
                                                     fontSize = 16.sp)
                                            }
                                        }
                                    }
                                    Row {
                                       Column(horizontalAlignment = Alignment.CenterHorizontally)
                                       {
                                            Column(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 16.dp , top = 24.dp),
                                                   horizontalAlignment = Alignment.CenterHorizontally,
                                                   verticalArrangement = Arrangement.Center)
                                            {
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = "Contact Information",
                                                         style = typography.h5,
                                                         fontSize = 18.sp)
                                                }
                                                Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = data.email.orEmpty() ,
                                                         style = typography.caption ,
                                                         fontSize = 16.sp)
                                                }
                                                Row(modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically)
                                                {
                                                    Text(text = data.contact_number.orEmpty() ,
                                                         style = typography.caption ,
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
                                                   Text(text = "Availability",
                                                        style = typography.h5,
                                                        fontSize = 18.sp)
                                               }
                                               Row(modifier = Modifier
                                                       .fillMaxWidth()
                                                       .padding(bottom = 8.dp),
                                                   horizontalArrangement = Arrangement.Start,
                                                   verticalAlignment = Alignment.CenterVertically)
                                               {
                                                   Text(text = "${data.slots.orEmpty()} slots for tomorrow",
                                                        style = typography.caption,
                                                        fontSize = 16.sp)
                                               }
                                           }
                                       }
                                    }
                                }
                            }
                            item {
                                Column(modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp),
                                       verticalArrangement = Arrangement.Bottom,
                                       horizontalAlignment = Alignment.CenterHorizontally)
                                {
                                    Button(
                                            onClick = { onNavigateToForm(data.id!!) },
                                            modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(55.dp),
                                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff15C3DD)),
                                            shape = RoundedCornerShape(25.dp))
                                    {
                                        Text(text = "Schedule an Appointment" , color = Color.White , style = typography.h6)
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