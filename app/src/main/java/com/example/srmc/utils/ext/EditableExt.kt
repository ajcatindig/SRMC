package com.example.srmc.utils.ext

import android.text.Editable

fun Editable?.toStringOrEmpty() : String = this?.toString() ?: ""