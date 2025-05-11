package com.artiuil.lab.cleverdictionary.ui.screens.auth.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthError
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalNavController
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalSnackbarManager
import com.artiuil.lab.cleverdictionary.ui.navigation.components.Screen
import com.artiuil.lab.cleverdictionary.ui.screens.auth.signin.components.rememberGoogleSignInHandler
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onSwitchMode: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current
    val snackbarManager = LocalSnackbarManager.current
    val coroutineScope = rememberCoroutineScope()

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val googleSignInHandler = rememberGoogleSignInHandler(
        onTokenReceived = { idToken ->
            viewModel.signInWithGoogle(idToken)
        },
        onError = { errorMsg ->
            coroutineScope.launch {
                snackbarManager.showMessage(errorMsg)
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is SignInUiState.Loading, is SignInUiState.SignedIn -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                SignInScreenContent(
                    onSwitchMode = onSwitchMode,
                    email = email,
                    password = password,
                    onEmailChanged = viewModel::onEmailChanged,
                    onPasswordChanged = viewModel::onPasswordChanged,
                    onForgotPassword = { viewModel.forgotPassword() },
                    onSignIn = { viewModel.signIn() },
                    onSignInWithGoogle = { googleSignInHandler() }
                )
            }
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is SignInUiState.SignedIn -> {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Auth.route) { inclusive = true }
                }

            }

            is SignInUiState.Success -> {}
            is SignInUiState.Error -> {
                val errorMessage = when ((uiState as SignInUiState.Error).error) {
                    AuthError.EmptyEmail -> context.getString(R.string.empty_email_error)
                    AuthError.EmptyCredentials -> context.getString(R.string.empty_credentials_error)
                    AuthError.IncorrectEmail -> context.getString(R.string.incorrect_email_error)
                    AuthError.IncorrectCredentials -> context.getString(R.string.incorrect_email_error)
                    AuthError.UnknownSignInError -> context.getString(R.string.unknown_sign_in_error)
                    AuthError.EmailNotVerified -> context.getString(R.string.unknown_sign_in_error)
                    else -> context.getString(R.string.unknown_sign_up_error)
                }
                snackbarManager.showMessage(errorMessage)

            }

            else -> {}
        }
    }
}

@Composable
fun SignInScreenContent(
    onSwitchMode: () -> Unit,
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignIn: () -> Unit,
    onSignInWithGoogle: () -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.matcha_hero),
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = { Text(text = stringResource(id = R.string.email_input_field_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = stringResource(id = R.string.email_text_field_description)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            label = { Text(text = stringResource(id = R.string.password_input_field_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(id = R.string.password_text_field_description)
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Filled.Close
                        else Icons.Filled.Search,
                        contentDescription = if (passwordVisible)
                            stringResource(id = R.string.hide_password_button_description)
                        else stringResource(id = R.string.show_password_button_description)
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.forgot_password_button_text),
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onForgotPassword() },
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSignIn,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.sign_in_button_text))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSignInWithGoogle,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign in with Google",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onSwitchMode) {
            Text(stringResource(R.string.don_t_have_an_account_text))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenContentPreview() {
    AppTheme {
        SignInScreenContent(
            email = "user@example.com",
            password = "password123",
            onEmailChanged = {},
            onPasswordChanged = {},
            onForgotPassword = {},
            onSignIn = {},
            onSwitchMode = {},
            onSignInWithGoogle = {}
        )
    }
}