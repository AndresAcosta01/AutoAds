package com.andres.autoads.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun StatusItem(
    modifier: Modifier = Modifier,
    titulo: String,
    activo: Boolean
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = titulo,
            fontSize = 16.sp
        )

        Text(
            text = if (activo) "✅ Activada" else "❌ Desactivada",
            fontSize = 16.sp
        )

    }

}