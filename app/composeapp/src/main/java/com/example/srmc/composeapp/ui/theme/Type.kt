package com.example.srmc.composeapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.R

private val inter = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_semibold, FontWeight.W600),
        Font(R.font.inter_bold, FontWeight.Bold)
                               )

private val universalStd = FontFamily(
        Font(R.font.universal_std)
                                     )

private val poppins = FontFamily(
        Font(R.font.poppins_regular) ,
        Font(R.font.poppins_medium, FontWeight.W500) ,
        Font(R.font.poppins_bold, FontWeight.Bold))

val typography = Typography(
        h2 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.W600,
                fontSize = 42.sp
                      ),
        h3 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.W600,
                fontSize = 36.sp
                      ),
        h4 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.W600,
                fontSize = 30.sp
                      ),
        h5 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.W600,
                fontSize = 24.sp
                      ),
        h6 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp
                      ),
        subtitle1 = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp
                             ),
        subtitle2 = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp
                             ),
        body1 = TextStyle(
                fontFamily = universalStd,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
                         ),
        body2 = TextStyle(
                fontFamily = universalStd,
                fontSize = 14.sp
                         ),
        button = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp
                          ),
        caption = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
                           ),
        overline = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight.W500,
                fontSize = 12.sp
                            )
                           )
