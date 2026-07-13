package com.andres.autoads.manager

object MotorAutoAds {


    private var activo = false



    fun iniciar() {

        activo = true

    }



    fun pausar() {

        activo = false

    }



    fun estaActivo(): Boolean {

        return activo

    }


}