package com.example.srmc.composeapp.component.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LockReset
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.core.model.User
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileCard(
        data : User ,
        onLogoutClick : () -> Unit,
        onAboutAppClick : () -> Unit,
        onChangePasswordClick : () -> Unit,
        onManageProfileClick : () -> Unit)
{
    Column(modifier = Modifier.verticalScroll(rememberScrollState()))
    {
        Card(
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(),
                shape = RoundedCornerShape(8.dp),
                elevation = 0.dp)
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center)
            {
                Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    //Image
                    Column(horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Row(horizontalArrangement = Arrangement.Center ,
                            modifier = Modifier
                                    .padding(16.dp))
                        {
                            GlideImage(
                                    imageModel = data.profile_photo_path ,
                                    modifier = Modifier.size(140.dp).clip(CircleShape) ,
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
                        Row(verticalAlignment = Alignment.CenterVertically ,
                            horizontalArrangement = Arrangement.Center ,
                            modifier = Modifier
                                    .padding(start = 16.dp , end = 16.dp , bottom = 8.dp))
                        {
                            Text(text = data.name ?: "No Name Provided" ,
                                 maxLines = 2 ,
                                 overflow = TextOverflow.Ellipsis ,
                                 style = typography.h5 ,
                                 fontSize = 22.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically ,
                            horizontalArrangement = Arrangement.Center ,
                            modifier = Modifier
                                    .padding(start = 16.dp , end = 16.dp , bottom = 8.dp))
                        {
                            Text(text = data.email ?: "No Email Provided" ,
                                 maxLines = 2 ,
                                 overflow = TextOverflow.Ellipsis ,
                                 style = typography.subtitle1 ,
                                 fontSize = 18.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically ,
                            horizontalArrangement = Arrangement.Center ,
                            modifier = Modifier
                                    .padding(start = 16.dp , end = 16.dp , bottom = 8.dp))
                        {
                            Text(text = data.contact_number ?: "No Number Provided" ,
                                 maxLines = 2 ,
                                 overflow = TextOverflow.Ellipsis ,
                                 style = typography.subtitle1 ,
                                 fontSize = 18.sp)
                        }
                    }
                }
                Row {
                    //Clickable Rows
                    Column(horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        //Manage Profile
                        Divider(modifier = Modifier.height(1.dp))
                        Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp , horizontal = 16.dp),
                               horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                        .selectable(
                                                selected = false , onClick = onManageProfileClick , role = Role.Button
                                                   )
                                        .fillMaxWidth())
                            {
                                Icon(imageVector = Icons.Outlined.Edit ,
                                     contentDescription = "",
                                     modifier = Modifier.padding(16.dp),
                                     tint = Color(0xff15C3DD))
                                Text(text = "Manage Profile",
                                     style = typography.subtitle1,
                                     fontSize = 16.sp,
                                     color = MaterialTheme.colors.onPrimary)
                            }
                        }
                        //Change Password
                        Divider(modifier = Modifier.height(1.dp))
                        Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp , vertical = 8.dp),
                               horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                        .selectable(
                                                selected = false , onClick = onChangePasswordClick , role = Role.Button
                                                   )
                                        .fillMaxWidth())
                            {
                                Icon(imageVector = Icons.Outlined.LockReset ,
                                     contentDescription = "",
                                     modifier = Modifier.padding(16.dp),
                                     tint = Color(0xff15C3DD))
                                Text(text = "Change Password",
                                     style = typography.subtitle1,
                                     fontSize = 16.sp,
                                     color = MaterialTheme.colors.onPrimary)
                            }
                        }
                        //About App
                        Divider(modifier = Modifier.height(1.dp))
                        Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp , vertical = 8.dp),
                               horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start ,
                                modifier = Modifier
                                        .selectable(
                                                selected = false , onClick = onAboutAppClick , role = Role.Button
                                                   )
                                        .fillMaxWidth())
                            {
                                Icon(imageVector = Icons.Outlined.Info ,
                                     contentDescription = "",
                                     modifier = Modifier.padding(16.dp),
                                     tint = Color(0xff15C3DD))
                                Text(text = "About App",
                                     style = typography.subtitle1,
                                     fontSize = 16.sp,
                                     color = MaterialTheme.colors.onPrimary)
                            }
                        }
                        //Logout
                        Divider(modifier = Modifier.height(1.dp))
                        Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp , vertical = 8.dp) ,
                               horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Row(verticalAlignment = Alignment.CenterVertically ,
                                horizontalArrangement = Arrangement.Start ,
                                modifier = Modifier
                                        .selectable(
                                                selected = false , onClick = onLogoutClick , role = Role.Button
                                                   )
                                        .fillMaxWidth())
                            {
                                Icon(imageVector = Icons.Outlined.Logout ,
                                     contentDescription = "" ,
                                     modifier = Modifier.padding(16.dp) ,
                                     tint = Color.Red)
                                Text(text = "Logout" ,
                                     style = typography.subtitle1 ,
                                     fontSize = 16.sp ,
                                     color = Color.Red)
                            }
                        }
                        Divider(modifier = Modifier.height(1.dp))
                    }
                }
            }
        }
    }
}