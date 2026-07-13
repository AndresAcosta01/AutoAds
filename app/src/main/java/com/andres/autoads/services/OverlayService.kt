package com.andres.autoads.services

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import com.andres.autoads.manager.MotorAutoAds
import com.andres.autoads.manager.ServicioEstado
import com.andres.autoads.manager.AppEvents


class OverlayService : Service() {


    private lateinit var windowManager: WindowManager


    private lateinit var panel: LinearLayout

    private lateinit var botonMotor: Button
    private lateinit var botonCerrar: Button
    private lateinit var botonOcultar: Button

    private lateinit var botonMostrar: Button


    private lateinit var params: WindowManager.LayoutParams
    private lateinit var mostrarParams: WindowManager.LayoutParams


    private var ultimoX = 0
    private var ultimoY = 0

    private var moviendo = false


    override fun onCreate() {

        super.onCreate()


        windowManager =
            getSystemService(
                WINDOW_SERVICE
            ) as WindowManager



        crearPanel()


    }


    private fun crearPanel() {


        panel = LinearLayout(this)

        panel.orientation =
            LinearLayout.HORIZONTAL



        botonMotor = Button(this)

        botonMotor.text = "▶"



        botonCerrar = Button(this)

        botonCerrar.text = "X"



        botonOcultar = Button(this)

        botonOcultar.text = "<"





        panel.addView(botonMotor)

        panel.addView(botonCerrar)

        panel.addView(botonOcultar)





        params =
            WindowManager.LayoutParams(

                WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,

                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,

                PixelFormat.TRANSLUCENT
            )



        params.gravity =
            Gravity.TOP or Gravity.START



        params.x = 100
        params.y = 300





        windowManager.addView(
            panel,
            params
        )

        ServicioEstado.activo = true




        crearBotonMostrar()


        // =========================
        // MOTOR
        // =========================


        botonMotor.setOnClickListener {


            if (
                MotorAutoAds.estaActivo()
            ) {


                MotorAutoAds.pausar()


            } else {


                MotorAutoAds.iniciar()

            }


            actualizarEstadoBoton()


        }


        // =========================
        // X = DETENER APP
        // =========================


        botonCerrar.setOnClickListener {


            MotorAutoAds.pausar()


            ServicioEstado.activo = false


            sendBroadcast(
                Intent(
                    AppEvents.DETENER_SERVICIO
                )
            )


            stopSelf()


        }


        // =========================
        // OCULTAR MENU
        // =========================


        botonOcultar.setOnClickListener {


            panel.visibility =
                View.GONE



            botonMostrar.visibility =
                View.VISIBLE


        }


        // Movimiento libre

        activarMovimiento(botonMotor)

        activarMovimiento(botonCerrar)

        activarMovimiento(botonOcultar)


    }


    private fun crearBotonMostrar() {


        botonMostrar =
            Button(this)


        botonMostrar.text = "◉"



        mostrarParams =
            WindowManager.LayoutParams(

                WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,

                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,

                PixelFormat.TRANSLUCENT

            )



        mostrarParams.gravity =
            Gravity.TOP or Gravity.START



        mostrarParams.x = 100
        mostrarParams.y = 300



        botonMostrar.visibility =
            View.GONE




        windowManager.addView(
            botonMostrar,
            mostrarParams
        )





        botonMostrar.setOnClickListener {


            panel.visibility =
                View.VISIBLE



            botonMostrar.visibility =
                View.GONE


        }



        activarMovimiento(botonMostrar)


    }


    private fun activarMovimiento(
        view: View
    ) {


        view.setOnTouchListener { _, event ->


            when (event.action) {


                MotionEvent.ACTION_DOWN -> {


                    ultimoX =
                        event.rawX.toInt()


                    ultimoY =
                        event.rawY.toInt()


                    moviendo = false


                    true

                }


                MotionEvent.ACTION_MOVE -> {


                    val nuevoX =
                        event.rawX.toInt()


                    val nuevoY =
                        event.rawY.toInt()


                    val dx =
                        nuevoX - ultimoX


                    val dy =
                        nuevoY - ultimoY





                    if (
                        kotlin.math.abs(dx) > 5 ||
                        kotlin.math.abs(dy) > 5
                    ) {

                        moviendo = true

                    }





                    params.x += dx

                    params.y += dy





                    windowManager.updateViewLayout(
                        panel,
                        params
                    )





                    mostrarParams.x =
                        params.x


                    mostrarParams.y =
                        params.y





                    windowManager.updateViewLayout(
                        botonMostrar,
                        mostrarParams
                    )






                    ultimoX =
                        nuevoX


                    ultimoY =
                        nuevoY




                    true

                }


                MotionEvent.ACTION_UP -> {


                    if (!moviendo) {

                        view.performClick()

                    }



                    true

                }


                else -> false


            }


        }


    }


    private fun actualizarEstadoBoton() {


        botonMotor.text =

            if (
                MotorAutoAds.estaActivo()
            ) {

                "⏸"

            } else {

                "▶"

            }


    }


    override fun onDestroy() {


        MotorAutoAds.pausar()



        if (
            ::panel.isInitialized
        ) {

            windowManager.removeView(
                panel
            )

        }



        if (
            ::botonMostrar.isInitialized
        ) {

            windowManager.removeView(
                botonMostrar
            )

        }



        super.onDestroy()


    }


    override fun onBind(
        intent: Intent?
    ): IBinder? {

        return null

    }


}