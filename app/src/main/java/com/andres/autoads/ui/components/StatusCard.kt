package com.andres.autoads.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andres.autoads.viewmodel.MainViewModel
import com.andres.autoads.models.ServiceState
import androidx.compose.foundation.clickable

@Composable
fun StatusCard(
    viewModel: MainViewModel,
    abrirAccesibilidad: () -> Unit,
    abrirSuperposicion: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            StatusItem(
                modifier = Modifier.clickable {
                    abrirAccesibilidad()
                },
                titulo = "Accesibilidad",
                activo = viewModel.accesibilidadActiva
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            StatusItem(
                modifier = Modifier.clickable {
                    abrirSuperposicion()
                },
                titulo = "Superposición",
                activo = viewModel.superposicionActiva
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            InfoItem(
                titulo = "Estado",
                valor = when (viewModel.serviceState) {
                    ServiceState.STOPPED -> "Servicio detenido"
                    ServiceState.STARTING -> "Iniciando"
                    ServiceState.RUNNING -> "En ejecución"
                    ServiceState.PAUSED -> "Pausado"
                    ServiceState.ERROR -> "Error"
                }
            )
        }

    }

}