package com.example.srmc.utils.ext

import android.graphics.drawable.Drawable
import android.widget.TextView

fun TextView.setDrawableLeft(drawable: Drawable?) {
    setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
}