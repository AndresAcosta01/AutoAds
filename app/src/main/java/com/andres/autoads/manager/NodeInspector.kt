package com.andres.autoads.manager

import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo

object NodeInspector {

    fun inspeccionar(root: AccessibilityNodeInfo?) {

        if (root == null)
            return

        recorrer(root, 0)

    }

    private fun recorrer(
        node: AccessibilityNodeInfo,
        nivel: Int
    ) {

        val espacios = " ".repeat(nivel * 2)

        if (
            node.text != null ||
            node.isClickable ||
            node.viewIdResourceName != null
        ) {

            Log.d(
                "NodeInspector",
                "$espacios" +
                        "texto=${node.text} | " +
                        "id=${node.viewIdResourceName} | " +
                        "click=${node.isClickable} | " +
                        "clase=${node.className}"
            )

        }

        for (i in 0 until node.childCount) {

            val hijo = node.getChild(i)

            if (hijo != null)
                recorrer(hijo, nivel + 1)

        }

    }

}