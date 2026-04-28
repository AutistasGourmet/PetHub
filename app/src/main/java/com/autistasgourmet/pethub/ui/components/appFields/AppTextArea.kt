package com.autistasgourmet.pethub.ui.components.appFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.autistasgourmet.pethub.ui.theme.PetHubTheme

@Composable
fun AppTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    minLines: Int = 3,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        placeholder = placeholder,
        singleLine = false,
        minLines = minLines,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences
        ),
        isError = isError,
        errorMessage = errorMessage,
        enabled = enabled
    )
}

@Composable
@Preview(showBackground = true)
fun AppTextAreaPreview() {
    PetHubTheme {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            var desc by remember {
                mutableStateOf(
                    "" +
                            "Trabajo desde casa 3 días a la semana, " +
                            "por lo que tengo tiempo para dedicar a una " +
                            "mascota. Me gusta dar paseos largos."
                )
            }

            AppTextArea(
                value = desc,
                onValueChange = { desc = it },
                label = "Detalles sobre el espacio y rutna",
                placeholder = "Escribe tus detalles"
            )
        }
    }
}