package com.autistasgourmet.pethub.ui.features.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.autistasgourmet.pethub.ui.components.commons.AppLogo
import com.autistasgourmet.pethub.ui.components.appButtons.AppClickableText
import com.autistasgourmet.pethub.ui.components.appFields.AppTextField
import com.autistasgourmet.pethub.ui.components.appButtons.AppPrimaryButton
import com.autistasgourmet.pethub.ui.features.login.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val email = viewModel.email
    val password = viewModel.password
    val isLoading = viewModel.isLoading
    val emailError = viewModel.emailError
    val passwordError = viewModel.passwordError

    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loginSuccess.collect { success ->
            if (success) onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        AppLogo()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "PetHub",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Conecta con animal-lovers y encuentra hogares para mascotas",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        AppTextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            label = "Correo Electrónico",
            placeholder = "ejemplo@correo.com",
            leadingIcon = Icons.Default.Email,
            isError = emailError != null,
            errorMessage = emailError
        )

        Spacer(modifier = Modifier.height(8.dp))

        AppTextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = "Contraseña",
            placeholder = "Tu contraseña",
            leadingIcon = Icons.Default.Lock,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            isError = passwordError != null,
            errorMessage = passwordError
        )

        Spacer(modifier = Modifier.height(40.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            AppPrimaryButton(
                text = "Iniciar Sesión",
                icon = Icons.Default.Login,
                onClick = viewModel::login
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row {
            Text(text = "¿No tienes cuenta? ", style = MaterialTheme.typography.bodyMedium)
            AppClickableText(
                text = "Regístrate",
                onClick = onNavigateToRegister
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
