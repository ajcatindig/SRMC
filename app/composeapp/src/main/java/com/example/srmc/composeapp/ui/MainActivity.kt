package com.example.srmc.composeapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.srmc.composeapp.ui.theme.SRMCTheme
import com.example.srmc.composeapp.ui.theme.buttonGreen
import com.example.srmc.core.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{

    @Inject
    lateinit var preferenceManager : PreferenceManager
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.srmc.composeapp.ui.theme.SRMCTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize() , color = Color.White) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name : String , modifier : Modifier = Modifier)
{
    Text(
            text = "Hello $name!" , modifier = modifier
        )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview()
{
    com.example.srmc.composeapp.ui.theme.SRMCTheme {
        Greeting("Android")
    }
}