package com.andres.autoads.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andres.autoads.models.ServiceState
import com.andres.autoads.ui.components.StatusCard
import com.andres.autoads.viewmodel.MainViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import com.andres.autoads.services.OverlayService


@Composable
fun AutoAdsScreen(
    viewModel: MainViewModel,
    abrirAccesibilidad: () -> Unit,
    abrirSuperposicion: () -> Unit,
    pedirCaptura: () -> Unit
) {

    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Text(
                text = "AUTOADS",
                fontSize = 40.sp,
                color = Color.Black
            )


            Spacer(
                modifier = Modifier.height(40.dp)
            )


            StatusCard(
                viewModel = viewModel,
                abrirAccesibilidad = abrirAccesibilidad,
                abrirSuperposicion = abrirSuperposicion
            )


            Spacer(
                modifier = Modifier.height(30.dp)
            )



            Button(

                colors = ButtonDefaults.buttonColors(

                    containerColor =
                        if (
                            viewModel.serviceState == ServiceState.RUNNING
                        )
                            Color.Red
                        else
                            Color(0xFF4CAF50)

                ),


                onClick = {


                    if (
                        viewModel.serviceState == ServiceState.STOPPED
                    ) {


                        if (
                            !viewModel.accesibilidadActiva
                        ) {


                            Toast.makeText(
                                context,
                                "Debes activar la accesibilidad",
                                Toast.LENGTH_SHORT
                            ).show()


                            return@Button

                        }



                        if (
                            !viewModel.superposicionActiva
                        ) {


                            Toast.makeText(
                                context,
                                "Debes activar la superposición",
                                Toast.LENGTH_SHORT
                            ).show()


                            return@Button

                        }


                        // AQUÍ SOLAMENTE PEDIMOS CAPTURA
                        pedirCaptura()


                    } else {


                        viewModel.detenerServicio()



                        context.stopService(
                            Intent(
                                context,
                                OverlayService::class.java
                            )
                        )


                    }


                }

            ) {


                Text(

                    if (
                        viewModel.serviceState == ServiceState.RUNNING
                    )

                        "DETENER"
                    else

                        "INICIAR"

                )


            }


        }


    }


}