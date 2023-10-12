package com.example.srmc.composeapp.component.scaffold.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.srmc.composeapp.R
import com.example.srmc.composeapp.ui.theme.typography

/**
 * Home usable Top app bar for the project
 */
@Composable
fun HomeTopBar(title : String = "Clinic Doctors")
{
    TopAppBar(
            title = {
                Row(horizontalArrangement = Arrangement.Start ,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()) {
                    Image(painter =  painterResource(id = R.drawable.ic_splashlogo),
                              contentDescription = "",
                              modifier = Modifier.padding(end = 10.dp).align(CenterVertically).size(40.dp))
                    Text(text = title,
                         color = MaterialTheme.colors.onPrimary ,
                         style = typography.h5,
                         modifier = Modifier.align(Alignment.CenterVertically))
                }
            },
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 0.dp
             )
}

@Composable
@Preview
fun PreviewTopBar()
{
    HomeTopBar()
}