package com.andres.autoads

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.andres.autoads.ui.screens.AutoAdsScreen
import com.andres.autoads.ui.theme.AutoAdsTheme
import com.andres.autoads.viewmodel.MainViewModel
import android.media.projection.MediaProjectionManager
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity
import com.andres.autoads.manager.MotorAutoAds
import com.andres.autoads.services.OverlayService
import com.andres.autoads.models.ServiceState
import android.content.BroadcastReceiver
import android.content.IntentFilter
import com.andres.autoads.manager.AppEvents

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val receptorDetener =
        object : BroadcastReceiver() {


            override fun onReceive(
                context: Context?,
                intent: Intent?
            ) {


                if (
                    intent?.action ==
                    AppEvents.DETENER_SERVICIO
                ) {

                    viewModel.detenerServicio()

                }


            }


        }

    private val capturaPantalla =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { resultado ->


            if (resultado.resultCode == Activity.RESULT_OK) {


                viewModel.activarCaptura()


                viewModel.iniciarServicio()


                startService(
                    Intent(
                        this,
                        OverlayService::class.java
                    )
                )


            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(
            receptorDetener,
            IntentFilter(
                AppEvents.DETENER_SERVICIO
            ),
            RECEIVER_NOT_EXPORTED
        )

        setContent {

            AutoAdsTheme {

                AutoAdsScreen(
                    viewModel = viewModel,

                    abrirAccesibilidad = {
                        startActivity(
                            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                        )
                    },

                    abrirSuperposicion = {
                        startActivity(
                            Intent(
                                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                android.net.Uri.parse("package:$packageName")
                            )
                        )
                    },

                    pedirCaptura = {
                        pedirCapturaPantalla()
                    }
                )

            }

        }
    }

    override fun onResume() {

        super.onResume()


        viewModel.actualizarAccesibilidad(
            estaAccesibilidadActiva(this)
        )


        viewModel.actualizarSuperposicion(
            permisoSuperposicionActivo(this)
        )
        
        viewModel.revisarEstadoServicio()

        
        viewModel.sincronizarEstado()

        if (
            !viewModel.accesibilidadActiva ||
            !viewModel.superposicionActiva
        ) {

            viewModel.detenerServicio()

        }

    }

    fun pedirCapturaPantalla() {


        val manager =
            getSystemService(
                Context.MEDIA_PROJECTION_SERVICE
            ) as MediaProjectionManager



        capturaPantalla.launch(
            manager.createScreenCaptureIntent()
        )

    }

    override fun onDestroy() {


        unregisterReceiver(
            receptorDetener
        )


        super.onDestroy()

    }
}

fun estaAccesibilidadActiva(context: Context): Boolean {

    val manager = context.getSystemService(
        AccessibilityManager::class.java
    )

    val serviciosActivos = manager.getEnabledAccessibilityServiceList(
        AccessibilityServiceInfo.FEEDBACK_ALL_MASK
    )

    return serviciosActivos.any {
        it.resolveInfo.serviceInfo.packageName == context.packageName
    }
}

fun permisoSuperposicionActivo(context: Context): Boolean {
    return Settings.canDrawOverlays(context)
}

