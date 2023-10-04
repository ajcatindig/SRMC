package com.example.srmc.composeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val primary = Color(0xFFFFFFFF)

val backgroundDay = Color(0xfff3f7f9)
val backgroundNight = Color(0xff1A191E)

val surfaceDay = Color(0xffffffff)
val surfaceNight = Color(0xFF38353F)

val black = Color(0xff000000)
val white = Color(0xffffffff)

val green = Color(0xff6FCF97)
val red = Color(0xffEB5757)

val buttonGreen = Color(0xff348252)
val buttonRed = Color(0xffC42929)

@Composable
fun getTextFieldHintColor(): Color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
