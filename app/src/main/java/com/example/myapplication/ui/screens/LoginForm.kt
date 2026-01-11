package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.model.LoginIntent
import com.example.myapplication.viewmodel.LoginViewModel


@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginState = viewModel.loginState.collectAsState()
    val state = loginState.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (state.loginResponse.success) {
            Text(text = state.loginResponse.message)
        } else {
            Text(text = "Iniciar sesión", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.handleIntent(LoginIntent.EmailChange(it)) },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.handleIntent(LoginIntent.PasswordChange(it)) },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (state.error.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.handleIntent(
                        LoginIntent.Login(
                            email = state.email,
                            password = state.password
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text("Entrar")
                }
            }
        }
    }
}
