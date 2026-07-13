package com.andres.autoads.manager

import android.view.accessibility.AccessibilityNodeInfo

object NodeActions {

    fun click(node: AccessibilityNodeInfo?): Boolean {

        if (node == null)
            return false

        if (node.isClickable)
            return node.performAction(
                AccessibilityNodeInfo.ACTION_CLICK
            )

        var parent = node.parent

        while (parent != null) {

            if (parent.isClickable)
                return parent.performAction(
                    AccessibilityNodeInfo.ACTION_CLICK
                )

            parent = parent.parent
        }

        return false

    }

}