package com.andres.autoads.manager

import android.graphics.Bitmap
import android.media.Image


object ScreenCaptureManager {


    private var pantallaActual: Bitmap? = null


    fun procesarImagen(image: Image) {


        val plane = image.planes[0]

        val buffer = plane.buffer


        val pixelStride = plane.pixelStride

        val rowStride = plane.rowStride


        val rowPadding =
            rowStride - pixelStride * image.width


        val bitmap = Bitmap.createBitmap(
            image.width + rowPadding / pixelStride,
            image.height,
            Bitmap.Config.ARGB_8888
        )



        bitmap.copyPixelsFromBuffer(buffer)



        pantallaActual = bitmap



        image.close()


    }


    fun obtenerPantalla(): Bitmap? {


        return pantallaActual


    }


}