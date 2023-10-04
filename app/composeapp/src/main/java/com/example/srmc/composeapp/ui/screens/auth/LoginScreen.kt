package com.example.srmc.composeapp.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.R
import com.example.srmc.composeapp.component.dialog.FailureDialog
import com.example.srmc.composeapp.component.dialog.LoaderDialog
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.SRMCPreview

@Composable
fun LoginScreen()
{

}

@Composable
fun LoginContent(
        isLoading : Boolean ,
        email : String ,
        password : String ,
        onEmailChange : (String) -> Unit ,
        onPasswordChange : (String) -> Unit ,
        onLoginClick : () -> Unit ,
        onForgotClick : () -> Unit ,
        onSignUpClick : () -> Unit ,
        error : String?)
{
    if (isLoading) {
        LoaderDialog()
    }

    if (error != null) {
        FailureDialog(failureMessage = error)
    }
    Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp, horizontal = 20.dp)
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState()))
    {
        TopGreeting()

        LoginForm(email = email ,
                  password = password ,
                  onEmailChange = onEmailChange ,
                  onPasswordChange = onPasswordChange,
                  onLoginClick = onLoginClick)

        ForgotPasswordLink(Modifier.align(Alignment.CenterHorizontally), onForgotClick = onForgotClick)

        SignUpLink(Modifier.align(Alignment.CenterHorizontally), onSignUpClick = onSignUpClick)
    }
}

@Composable
fun TopGreeting()
{
    Column(Modifier.fillMaxWidth())
    {
        Image(
                painter =  painterResource(id = R.drawable.app_logo) ,
                contentDescription = "",
                modifier = Modifier
                        .padding(top = 50.dp)
                        .requiredSize(190.dp)
                        .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillBounds)
    }

    Text(
            text = "WELCOME" ,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp,
            modifier = Modifier
                    .padding(horizontal = 16.dp , vertical = 20.dp)
                    .fillMaxWidth() ,
            textAlign = TextAlign.Center)
}

@Composable
fun LoginForm(
        email : String,
        password : String,
        onEmailChange : (String) -> Unit,
        onPasswordChange : (String) -> Unit,
        onLoginClick : () -> Unit)
{
    val focusManager = LocalFocusManager.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isValidate by derivedStateOf { email.isNotBlank() && password.isNotBlank() }

    /** [EMAIL]*/
    OutlinedTextField(
            value = email ,
            onValueChange = onEmailChange ,
            label = { Text(text = "Email") } ,
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp , vertical = 8.dp),
            leadingIcon = { Icon(Icons.Outlined.Person , "") } ,
            textStyle = TextStyle(color = MaterialTheme.colors.onPrimary , fontSize = 16.sp) ,
            shape = RoundedCornerShape(25.dp) ,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email , imeAction = ImeAction.Next) ,
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }) ,
            singleLine = true)

    /** [PASSWORD]*/
    OutlinedTextField(
            value = password ,
            onValueChange = onPasswordChange ,
            label = { Text(text = "Password") } ,
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp , vertical = 8.dp) ,
            leadingIcon = { Icon(Icons.Outlined.Lock, "") } ,
            textStyle = TextStyle(color = MaterialTheme.colors.onPrimary, fontSize = 16.sp) ,
            shape = RoundedCornerShape(25.dp) ,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done) ,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }) ,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation() ,
            trailingIcon = { IconButton(onClick = { isPasswordVisible = ! isPasswordVisible }) {
                Icon(imageVector = if (isPasswordVisible) Icons.Outlined.Visibility
                else Icons.Outlined.VisibilityOff, contentDescription = "")
            }
            } ,
            singleLine = true)

    /** [BUTTON]*/
    Button(
            onClick = onLoginClick ,
            enabled = isValidate ,
            modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(vertical = 16.dp , horizontal = 16.dp) ,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff348252)) ,
            shape = RoundedCornerShape(25.dp))
    {
        Text(text = "Log in" , color = Color.White , style = typography.h6)
    }
}

@Composable
fun ForgotPasswordLink(modifier : Modifier, onForgotClick : () -> Unit)
{
    Text(text = buildAnnotatedString {
        append("Forgot Password?")
        addStyle(SpanStyle(color = Color.Blue), start = 0, this.length)
        toAnnotatedString()
    },
         style = typography.subtitle1,
         modifier = modifier
                 .padding(vertical = 16.dp , horizontal = 16.dp)
                 .clickable(onClick = onForgotClick))
}

@Composable
fun SignUpLink(modifier : Modifier, onSignUpClick : () -> Unit)
{
    Text(text = buildAnnotatedString {
        append("Don't have an account? Sign up")
        addStyle(SpanStyle(color = Color.Blue), start = 23, this.length)
        toAnnotatedString()
    },
         style = typography.subtitle1,
         modifier = modifier
                 .padding(vertical = 50.dp , horizontal = 16.dp)
                 .clickable(onClick = onSignUpClick))
}

@Preview
@Composable
fun PreviewLoginContent() = SRMCPreview {
    LoginContent(
            isLoading = false ,
            email =  "johndoe@email.com",
            password =  "password",
            onEmailChange =  {},
            onPasswordChange =  {},
            onLoginClick = {} ,
            onSignUpClick = {} ,
            onForgotClick = {},
            error = null
                )
}


