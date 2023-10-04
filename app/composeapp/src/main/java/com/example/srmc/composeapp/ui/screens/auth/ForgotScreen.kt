package com.example.srmc.composeapp.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.R
import com.example.srmc.composeapp.component.dialog.FailureDialog
import com.example.srmc.composeapp.component.dialog.LoaderDialog
import com.example.srmc.composeapp.component.dialog.SuccessDialog
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.SRMCPreview

@Composable
fun ForgotScreen(
        onNavigateUp : () -> Unit
                )
{

}

@Composable
fun ForgotContent(
        isLoading : Boolean ,
        email : String ,
        onEmailChange : (String) -> Unit ,
        isValidEmail : Boolean,
        onSubmitClick : () -> Unit ,
        onBackClick : () -> Unit ,
        isSuccess : String?,
        error : String?)
{
    if (isLoading) {
        LoaderDialog()
    }

    if (error != null) {
        FailureDialog(error)
    }

    if (isSuccess != null) {
        SuccessDialog(isSuccess)
    }

    Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState()))
    {
        BackToLogin(Modifier.align(Alignment.Start), onBackClick = onBackClick)

        Column(Modifier.padding(horizontal = 16.dp))
        {
            TopMessage()

            ForgotForm(email = email ,
                       onEmailChange = onEmailChange,
                       onSubmitClick = onSubmitClick,
                       isValidEmail = isValidEmail)
        }
    }
}

@Composable
fun TopMessage()
{
    Column(
            Modifier
                    .fillMaxWidth()
                    .padding(top = 55.dp))
    {
        Image(
                painter = painterResource(id = R.drawable.ic_forgot) ,
                contentDescription = "" ,
                modifier = Modifier
                        .padding(top = 10.dp)
                        .requiredSize(160.dp)
                        .align(Alignment.CenterHorizontally))
    }
    Text(
            text = "Forgot Password?",
            style = typography.h4,
            modifier = Modifier
                    .padding(horizontal = 16.dp , vertical = 16.dp)
                    .fillMaxWidth(),
            textAlign = TextAlign.Center)
    Text(
            text = "To reset your password, please enter the email associated with the account you are trying to login. " +
                   "A password reset email will be sent to you shortly after.",
            style = typography.subtitle1,
            modifier = Modifier
                    .padding(horizontal = 16.dp , vertical = 16.dp)
                    .fillMaxWidth(),
            textAlign = TextAlign.Center)
}

@Composable
fun ForgotForm(
        email : String,
        onEmailChange : (String) -> Unit,
        onSubmitClick : () -> Unit,
        isValidEmail : Boolean)
{
    val focusManager = LocalFocusManager.current
    val isValidate by derivedStateOf{ email.isNotBlank() }

    /** [EMAIL]*/
    OutlinedTextField(
            value = email ,
            onValueChange = onEmailChange ,
            label = { Text(text = "Email") } ,
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp , vertical = 8.dp) ,
            leadingIcon = { Icon(Icons.Outlined.MailOutline , "") } ,
            textStyle = TextStyle(color = MaterialTheme.colors.onPrimary , fontSize = 16.sp) ,
            shape = RoundedCornerShape(25.dp) ,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email , imeAction = ImeAction.Done) ,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }) ,
            singleLine = true ,
            isError = !isValidEmail)

    /** [SUBMIT] */
    Button(
            onClick = onSubmitClick ,
            enabled = isValidate ,
            modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(vertical = 16.dp, horizontal = 16.dp) ,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff348252)) ,
            shape = RoundedCornerShape(25.dp)
          )
    {
        Text(text = "Submit" , color = Color.White , style = typography.h6)
    }

}

@Composable
fun BackToLogin(modifier : Modifier, onBackClick : () -> Unit)
{
    IconButton(onClick = onBackClick, modifier.padding(horizontal = 8.dp, vertical = 20.dp))
    {
        Icon(Icons.Outlined.ArrowBackIos , contentDescription = "",
             tint = MaterialTheme.colors.onPrimary)
    }
}

@Preview
@Composable
fun PreviewForgotContent() = SRMCPreview {
    ForgotContent(
            isLoading = false ,
            email =  "johndoe@gmail.com",
            onEmailChange =  {},
            isValidEmail =  false,
            onSubmitClick = {} ,
            onBackClick = {} ,
            error = null,
            isSuccess = null)
}