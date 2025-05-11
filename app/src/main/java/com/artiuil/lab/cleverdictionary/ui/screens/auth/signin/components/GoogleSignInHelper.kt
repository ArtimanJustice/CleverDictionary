@file:Suppress("DEPRECATION")

package com.artiuil.lab.cleverdictionary.ui.screens.auth.signin.components

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.artiuil.lab.cleverdictionary.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun rememberGoogleSignInHandler(
    context: Context = LocalContext.current,
    onTokenReceived: (String) -> Unit,
    onError: (String) -> Unit
): () -> Unit {

    val coroutineScope = rememberCoroutineScope()
    val signInClient = Identity.getSignInClient(context)
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            coroutineScope.launch {
                @Suppress("DEPRECATION")
                val signInCredential = signInClient.getSignInCredentialFromIntent(result.data)
                val idToken = signInCredential.googleIdToken
                if (!idToken.isNullOrEmpty()) {
                    onTokenReceived(idToken)
                } else {
                    onError("Google ID token is missing!")
                }
            }
        } else {
            onError("Google Sign-In cancelled")
        }
    }
    return {
        signInClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    launcher.launch(intentSenderRequest)
                } catch (e: Exception) {
                    onError("Error launching sign-in intent: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener { exception ->
                onError("Google Sign-In error: ${exception.localizedMessage}")
            }
    }
}
