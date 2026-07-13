package com.andres.autoads.services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.andres.autoads.manager.AutomationEngine
import com.andres.autoads.manager.MotorAutoAds
import com.andres.autoads.manager.SessionManager


class AutoAdsAccessibilityService : AccessibilityService() {


    companion object {

        private const val TAG = "AutoAds"

        var instancia: AutoAdsAccessibilityService? = null

    }


    override fun onServiceConnected() {

        super.onServiceConnected()


        instancia = this


        Log.e(
            "AUTOADS",
            "******** SERVICIO CONECTADO ********"
        )


    }


    override fun onAccessibilityEvent(
        event: AccessibilityEvent?
    ) {


        if (!MotorAutoAds.estaActivo())
            return



        if (event == null)
            return


        val packageName =
            event.packageName?.toString()
                ?: return


        val appName = try {


            val appInfo =
                packageManager.getApplicationInfo(
                    packageName,
                    0
                )


            packageManager.getApplicationLabel(
                appInfo
            ).toString()


        } catch (e: Exception) {


            packageName


        }





        SessionManager.actualizarAplicacion(
            appName
        )



        SessionManager.actualizarPaquete(
            packageName
        )



        SessionManager.actualizarRoot(
            rootInActiveWindow
        )


        if (MotorAutoAds.estaActivo()) {

            AutomationEngine.ejecutar()

        }


    }


    override fun onInterrupt() {


        Log.d(
            TAG,
            "Servicio interrumpido"
        )


    }


    fun clickPantalla(
        x: Int,
        y: Int
    ) {


        val path =
            Path()



        path.moveTo(
            x.toFloat(),
            y.toFloat()
        )


        val gesture =
            GestureDescription.Builder()
                .addStroke(
                    GestureDescription.StrokeDescription(
                        path,
                        0,
                        100
                    )
                )
                .build()





        dispatchGesture(
            gesture,
            null,
            null
        )



        Log.d(
            TAG,
            "TOQUE REAL EN x=$x y=$y"
        )

    }


    override fun onDestroy() {

        super.onDestroy()

        instancia = null

    }


}