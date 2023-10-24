package com.example.srmc.composeapp.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.BuildConfig
import com.example.srmc.composeapp.R
import com.example.srmc.composeapp.component.scaffold.SRMCScaffold
import com.example.srmc.composeapp.component.scaffold.detail.AboutAppTopBar
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.IntentUtils
import com.example.srmc.composeapp.utils.SRMCPreview

@Composable
fun AboutAppScreen(onNavigateUp : () -> Unit)
{
    SRMCScaffold(
            topAppBar = {
        AboutAppTopBar(onNavigateUp = onNavigateUp)
    },
            content = {
                AboutContent()
            })
}

@Composable
fun AboutContent()
{
    val context = LocalContext.current
    val appUrl = "https://srmc-front.mcbroad.com"

    Surface(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface))
    {
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 180.dp),
               horizontalAlignment = Alignment.CenterHorizontally)
        {
            Column(modifier = Modifier
                    .fillMaxWidth())
            {
                Row(modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center)
                {
                    val image = painterResource(id = R.drawable.app_logo)
                    Image(painter = image ,
                          contentDescription = "",
                          modifier = Modifier.requiredSize(200.dp),
                          alignment = Alignment.Center)
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center)
                {
                    Text(text = "SRMC - Mobile App",
                         textAlign = TextAlign.Center,
                         fontSize = 30.sp,
                         style = typography.h5,
                         color = MaterialTheme.colors.onPrimary)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center)
                {
                    Text(text = "v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                         textAlign = TextAlign.Center,
                         fontSize = 20.sp,
                         style = typography.subtitle1,
                         color = MaterialTheme.colors.onPrimary)
                }
                Spacer(modifier = Modifier.height(70.dp))
                Row(modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center)
                {
                    Button(
                            onClick = { IntentUtils.launchBrowser(context, appUrl) },
                            modifier = Modifier
                                    .height(50.dp)
                                    .padding(horizontal = 32.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF15C3DD)),
                            shape = RoundedCornerShape(25.dp))
                    {
                        Text(text = "Visit our Website", color = Color.White, style = typography.h6)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAboutContent() = SRMCPreview {
    AboutContent()
}