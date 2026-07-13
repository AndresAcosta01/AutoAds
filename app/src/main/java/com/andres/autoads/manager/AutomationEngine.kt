package com.andres.autoads.manager

import android.graphics.Point
import android.os.SystemClock
import android.util.Log


object AutomationEngine {


    private const val TAG = "AutomationEngine"


    private var ultimoClick = 0L


    private const val TIEMPO_ENTRE_CLICKS = 3000L



    fun ejecutar() {


        val pantalla =
            ScreenCaptureManager.obtenerPantalla()
                ?: return



        val puntoX =
            XDetector.buscar(pantalla)



        if (puntoX != null) {


            Log.d(
                TAG,
                "X VISUAL ENCONTRADA x=${puntoX.x} y=${puntoX.y}"
            )


            ejecutarClickVisual(puntoX)


        }


    }





    private fun ejecutarClickVisual(
        punto: Point
    ) {


        val ahora =
            SystemClock.elapsedRealtime()



        if (
            ahora - ultimoClick >= TIEMPO_ENTRE_CLICKS
        ) {



            Log.d(
                TAG,
                "CLICK VISUAL EJECUTADO x=${punto.x} y=${punto.y}"
            )



            GestureExecutor.click(
                punto.x,
                punto.y
            )



            ultimoClick = ahora


        } else {


            Log.d(
                TAG,
                "CLICK BLOQUEADO POR TIEMPO"
            )


        }


    }


}