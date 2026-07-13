package com.andres.autoads.manager

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point


object XDetector {


    fun buscar(bitmap: Bitmap): Point? {


        val ancho = bitmap.width
        val alto = bitmap.height


        for (y in 50 until alto - 50) {


            for (x in 50 until ancho - 50) {


                if (esBlanco(bitmap.getPixel(x,y))) {


                    var diagonal1 = 0
                    var diagonal2 = 0


                    for (i in 0..20) {


                        if(
                            esBlanco(
                                bitmap.getPixel(
                                    x+i,
                                    y+i
                                )
                            )
                        ){

                            diagonal1++

                        }



                        if(
                            esBlanco(
                                bitmap.getPixel(
                                    x+i,
                                    y-i
                                )
                            )
                        ){

                            diagonal2++

                        }

                    }



                    if(
                        diagonal1 > 15 &&
                        diagonal2 > 15
                    ){

                        return Point(x,y)

                    }

                }


            }

        }


        return null

    }



    private fun esBlanco(color:Int):Boolean{


        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)


        return r > 220 &&
                g > 220 &&
                b > 220

    }


}