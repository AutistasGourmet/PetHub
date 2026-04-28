package com.autistasgourmet.pethub.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autistasgourmet.pethub.ui.theme.PetHubTheme

@Composable
fun AppCheckBox(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AppCheckboxPreview() {
    var isCheckedVac by remember { mutableStateOf(false) }
    var isCheckedEst by remember { mutableStateOf(false) }
    var isCheckedDes by remember { mutableStateOf(false) }

    PetHubTheme {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            AppCheckBox(
                label = "Vacunado",
                checked = isCheckedVac,
                onCheckedChange = { isCheckedVac = it }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

            AppCheckBox(
                label = "Estelerizado / Castrado",
                checked = isCheckedEst,
                onCheckedChange = { isCheckedEst = it }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

            AppCheckBox(
                label = "Desparasitado",
                checked = isCheckedDes,
                onCheckedChange = { isCheckedDes = it }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

            AppCheckBox(
                label = "La vivienda cuenta con patio, terraza o jardin seguro",
                checked = isCheckedDes,
                onCheckedChange = { isCheckedDes = it }
            )
        }
    }
}
