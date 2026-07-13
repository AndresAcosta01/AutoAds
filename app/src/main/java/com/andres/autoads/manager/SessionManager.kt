package com.andres.autoads.manager

import android.view.accessibility.AccessibilityNodeInfo

object SessionManager {

    var aplicacionActual: String = ""
        private set

    var paqueteActual: String = ""
        private set

    var rootActual: AccessibilityNodeInfo? = null
        private set

    fun actualizarAplicacion(nombre: String) {
        aplicacionActual = nombre
    }

    fun actualizarPaquete(paquete: String) {
        paqueteActual = paquete
    }

    fun actualizarRoot(root: AccessibilityNodeInfo?) {
        rootActual = root
    }

    fun obtenerRoot(): AccessibilityNodeInfo? {
        return rootActual
    }

    fun limpiar() {
        aplicacionActual = ""
        paqueteActual = ""
        rootActual = null
    }

}