package com.andres.autoads.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.andres.autoads.manager.MotorAutoAds
import com.andres.autoads.manager.ServicioEstado
import com.andres.autoads.models.ServiceState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class MainViewModel : ViewModel() {


    var capturaActiva by mutableStateOf(false)
        private set


    fun activarCaptura() {
        capturaActiva = true
    }


    fun desactivarCaptura() {
        capturaActiva = false
    }


    var accesibilidadActiva by mutableStateOf(false)
        private set


    fun actualizarAccesibilidad(activa: Boolean) {

        accesibilidadActiva = activa

    }


    var superposicionActiva by mutableStateOf(false)
        private set


    fun actualizarSuperposicion(activa: Boolean) {

        superposicionActiva = activa

    }


    var serviceState by mutableStateOf(ServiceState.STOPPED)
        private set

    fun sincronizarEstado() {


        serviceState =
            if (ServicioEstado.activo) {

                ServiceState.RUNNING

            } else {

                ServiceState.STOPPED

            }


    }


    fun actualizarEstadoServicio() {


        serviceState =

            if (ServicioEstado.activo) {

                ServiceState.RUNNING

            } else {

                ServiceState.STOPPED

            }

    }


    fun updateServiceState(newState: ServiceState) {

        serviceState = newState

    }


    fun iniciarServicio(): Boolean {


        if (!accesibilidadActiva)
            return false


        if (!superposicionActiva)
            return false



        ServicioEstado.activo = true


        serviceState =
            ServiceState.RUNNING



        return true

    }


    fun detenerServicio() {


        MotorAutoAds.pausar()


        ServicioEstado.activo = false


        serviceState =
            ServiceState.STOPPED

    }

    fun revisarEstadoServicio() {

        serviceState =
            if (ServicioEstado.activo) {

                ServiceState.RUNNING

            } else {

                ServiceState.STOPPED

            }

    }


}

