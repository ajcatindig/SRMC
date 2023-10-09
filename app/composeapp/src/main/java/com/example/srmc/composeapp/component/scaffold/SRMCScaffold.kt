package com.example.srmc.composeapp.component.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.srmc.composeapp.component.dialog.FailureDialog
import com.example.srmc.composeapp.component.dialog.LoaderDialog

/**
 * Compose's wrapped Scaffold for this project
 */
@Composable
fun SRMCScaffold(
        modifier : Modifier = Modifier,
        topAppBar : @Composable () -> Unit,
        content : @Composable (PaddingValues) -> Unit = {},
        isLoading : Boolean = false,
        error : String? = null)
{
    if (isLoading) {
        LoaderDialog()
    }
    if (error != null) {
        FailureDialog(error)
    }
    Scaffold(
            modifier = modifier,
            topBar = topAppBar,
            content = content)
}