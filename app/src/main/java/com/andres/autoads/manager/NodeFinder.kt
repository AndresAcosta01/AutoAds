package com.andres.autoads.manager

import android.view.accessibility.AccessibilityNodeInfo

object NodeFinder {

    fun buscarPorTexto(
        root: AccessibilityNodeInfo?,
        texto: String
    ): AccessibilityNodeInfo? {

        if (root == null)
            return null

        val resultados = root.findAccessibilityNodeInfosByText(texto)

        if (resultados.isEmpty())
            return null

        return resultados.first()

    }

}