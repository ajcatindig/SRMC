package com.example.srmc.composeapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
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
import com.example.srmc.composeapp.component.dialog.SuccessDialog
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.SRMCPreview
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.view.viewmodel.auth.RegisterViewModel

@Composable
fun RegisterScreen(
        onNavigateUp : () -> Unit,
        viewModel : RegisterViewModel)
{
    val state by viewModel.collectState()

    RegisterContent(
            isLoading = state.isLoading ,
            username =  state.username,
            onUsernameChange =  viewModel::setUsername,
            email =  state.email,
            onEmailChange =  viewModel::setEmail,
            password =  state.password,
            onPasswordChange =  viewModel::setPassword,
            confirmPassword =  state.confirmPassword,
            onConfirmPasswordChange =  viewModel::setConfirmPassword,
            isValidConfirmPassword =  state.isValidConfirmPassword ?: true,
            onNavigateUp = onNavigateUp ,
            onSignUpClick = viewModel::register ,
            isValidUsername =  state.isValidUsername ?: true,
            isValidPassword =  state.isValidPassword ?: true,
            isValidEmail =  state.isValidEmail ?: true,
            isSuccess =  state.isSuccess,
            error = state.error)
}

@Composable
fun RegisterContent(
        isLoading : Boolean,
        username : String,
        onUsernameChange : (String) -> Unit,
        email : String,
        onEmailChange : (String) -> Unit,
        password : String,
        onPasswordChange : (String) -> Unit,
        confirmPassword : String,
        onConfirmPasswordChange : (String) -> Unit,
        isValidConfirmPassword : Boolean,
        onNavigateUp : () -> Unit,
        onSignUpClick : () -> Unit,
        isValidUsername : Boolean,
        isValidPassword : Boolean,
        isValidEmail : Boolean,
        isSuccess : String?,
        error : String?)
{
    if (isLoading) {
        LoaderDialog()
    }

    if (error != null) {
        FailureDialog(failureMessage = error)
    }

    if (isSuccess != null) {
        SuccessDialog(isSuccess)
    }

    Column(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp , horizontal = 20.dp)
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState()))
    {
        Text(text = "Create an Account" ,
             fontWeight = FontWeight.ExtraBold ,
             fontSize = 35.sp ,
             textAlign = TextAlign.Center,
             modifier = Modifier
                     .padding(start = 16.dp , end = 16.dp , top = 60.dp , bottom = 16.dp)
                     .fillMaxWidth())

        RegisterForm(
                username = username ,
                onUsernameChange = onUsernameChange ,
                isValidUsername =  isValidUsername,
                email =  email,
                onEmailChange =  onEmailChange,
                isValidEmail =  isValidEmail,
                password =  password,
                onPasswordChange =  onPasswordChange,
                isValidPassword =  isValidPassword,
                confirmPassword =  confirmPassword,
                onConfirmPasswordChange =  onConfirmPasswordChange,
                isValidConfirmPassword = isValidConfirmPassword,
                onSignUpClick = onSignUpClick)

        LoginLink(Modifier.align(Alignment.CenterHorizontally) , onLoginClick = onNavigateUp)
    }
}

@Composable
fun RegisterForm(
        username : String ,
        onUsernameChange : (String) -> Unit ,
        isValidUsername : Boolean ,
        email : String ,
        onEmailChange : (String) -> Unit ,
        isValidEmail : Boolean ,
        password : String ,
        onPasswordChange : (String) -> Unit ,
        isValidPassword : Boolean ,
        confirmPassword : String ,
        onConfirmPasswordChange : (String) -> Unit ,
        isValidConfirmPassword : Boolean ,
        onSignUpClick : () -> Unit)
{
    val focusManager = LocalFocusManager.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isValidate by derivedStateOf {
        email.isNotBlank() && password.isNotBlank() && username.isNotBlank() && confirmPassword.isNotBlank()
    }
    val helperText = ""

    Column(
            Modifier.padding(start = 16.dp , top = 32.dp , end = 16.dp , bottom = 16.dp)) {
        /** [username]*/
        OutlinedTextField(value = username ,
                          onValueChange = onUsernameChange ,
                          label = { Text(text = "Username") } ,
                          modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(vertical = 8.dp) ,
                          leadingIcon = { Icon(Icons.Outlined.Person , "") } ,
                          textStyle = TextStyle(
                                  color = MaterialTheme.colors.onPrimary , fontSize = 16.sp
                                               ) ,
                          shape = RoundedCornerShape(25.dp) ,
                          keyboardOptions = KeyboardOptions(
                                  keyboardType = KeyboardType.Email , imeAction = ImeAction.Next
                                                           ) ,
                          keyboardActions = KeyboardActions(onNext = {
                              focusManager.moveFocus(focusDirection = FocusDirection.Down)
                          }) ,
                          singleLine = true ,
                          isError = ! isValidUsername
                         )
        if (helperText.isEmpty())
        {
            Spacer(modifier = Modifier.padding(1.dp))
            Text(
                    stringResource(id = R.string.message_field_username_invalid) ,
                    style = MaterialTheme.typography.caption ,
                    fontStyle = FontStyle.Italic
                )
        }

        /** [email]*/
        OutlinedTextField(value = email ,
                          onValueChange = onEmailChange ,
                          label = { Text(text = "Email") } ,
                          modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(vertical = 8.dp) ,
                          leadingIcon = { Icon(Icons.Outlined.Mail , "") } ,
                          textStyle = TextStyle(
                                  color = MaterialTheme.colors.onPrimary , fontSize = 16.sp
                                               ) ,
                          shape = RoundedCornerShape(25.dp) ,
                          keyboardOptions = KeyboardOptions(
                                  keyboardType = KeyboardType.Email , imeAction = ImeAction.Next
                                                           ) ,
                          keyboardActions = KeyboardActions(onNext = {
                              focusManager.moveFocus(focusDirection = FocusDirection.Down)
                          }) ,
                          singleLine = true ,
                          isError = ! isValidEmail
                         )
        if (helperText.isEmpty())
        {
            Spacer(modifier = Modifier.padding(1.dp))
            Text(
                    stringResource(id = R.string.message_field_email_invalid) ,
                    style = MaterialTheme.typography.caption ,
                    fontStyle = FontStyle.Italic
                )
        }

        /** [password]*/
        OutlinedTextField(value = password ,
                          onValueChange = onPasswordChange ,
                          label = { Text(text = "Password") } ,
                          modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(vertical = 8.dp) ,
                          leadingIcon = { Icon(Icons.Outlined.Lock , "") } ,
                          textStyle = TextStyle(
                                  color = MaterialTheme.colors.onPrimary , fontSize = 16.sp
                                               ) ,
                          shape = RoundedCornerShape(25.dp) ,
                          keyboardOptions = KeyboardOptions(
                                  keyboardType = KeyboardType.Password , imeAction = ImeAction.Next
                                                           ) ,
                          keyboardActions = KeyboardActions(onNext = {
                              focusManager.moveFocus(focusDirection = FocusDirection.Down)
                          }) ,
                          visualTransformation = if (isPasswordVisible) VisualTransformation.None
                          else PasswordVisualTransformation() ,
                          trailingIcon = {
                              IconButton(onClick = {
                                  isPasswordVisible = ! isPasswordVisible
                              }) {
                                  Icon(
                                          imageVector = if (isPasswordVisible) Icons.Outlined.Visibility
                                          else Icons.Outlined.VisibilityOff , contentDescription = ""
                                      )
                              }
                          } ,
                          singleLine = true ,
                          isError = ! isValidPassword)
        if (helperText.isEmpty())
        {
            Spacer(modifier = Modifier.padding(1.dp))
            Text(
                    stringResource(id = R.string.message_field_password_invalid) ,
                    style = MaterialTheme.typography.caption ,
                    fontStyle = FontStyle.Italic
                )
        }

        /** [confirmPassword]*/
        OutlinedTextField(
                value = confirmPassword ,
                onValueChange = onConfirmPasswordChange ,
                label = { Text(text = "Confirm Password") } ,
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp) ,
                leadingIcon = { Icon(Icons.Outlined.Password , contentDescription = "") } ,
                textStyle = TextStyle(
                        color = MaterialTheme.colors.onPrimary , fontSize = 16.sp
                                     ) ,
                shape = RoundedCornerShape(25.dp) ,
                keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password , imeAction = ImeAction.Done
                                                 ) ,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }) ,
                visualTransformation = PasswordVisualTransformation() ,
                singleLine = true ,
                isError = ! isValidConfirmPassword
                         )
        if (helperText.isNotEmpty())
        {
            Spacer(modifier = Modifier.padding(1.dp))
            Text(
                    stringResource(id = R.string.message_password_mismatched) ,
                    style = MaterialTheme.typography.caption ,
                    fontStyle = FontStyle.Italic
                )
        }

        /** [BUTTON]*/
        Button(onClick = onSignUpClick ,
               enabled = isValidate ,
               modifier = Modifier
                       .fillMaxWidth()
                       .height(85.dp)
                       .padding(vertical = 16.dp) ,
               colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff348252)) ,
               shape = RoundedCornerShape(25.dp))
        {
            Text(text = "Sign up" , color = Color.White , style = typography.h6)
        }
    }
}

@Composable
private fun LoginLink(modifier : Modifier, onLoginClick : () -> Unit)
{
    Text(
            text = buildAnnotatedString {
                append("Already have an account? Login")
                addStyle(SpanStyle(color = Color(0xff15C3DD)) , 24 , this.length)
            },
            style = typography.subtitle1,
            modifier = modifier
                    .padding(vertical = 50.dp , horizontal = 16.dp)
                    .clickable(onClick = onLoginClick))
}

@Preview
@Composable
fun PreviewRegisterContent() = SRMCPreview {
    RegisterContent(
            isLoading = false ,
            username =  "johndoe",
            onUsernameChange =  {},
            email =  "sample@email.com",
            onEmailChange =  {},
            password =  "password",
            onPasswordChange = {} ,
            confirmPassword =  "password",
            onConfirmPasswordChange =  {},
            isValidConfirmPassword =  false,
            onNavigateUp = {} ,
            onSignUpClick = {} ,
            isValidUsername = false ,
            isValidPassword =  false,
            isValidEmail =  false,
            error = null,
            isSuccess = null)
}