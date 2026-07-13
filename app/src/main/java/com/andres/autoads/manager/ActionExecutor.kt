package com.andres.autoads.manager

import android.view.accessibility.AccessibilityNodeInfo

object ActionExecutor {

    fun click(node: AccessibilityNodeInfo): Boolean {

        var actual: AccessibilityNodeInfo? = node

        while (actual != null) {

            if (actual.isClickable) {
                return actual.performAction(
                    AccessibilityNodeInfo.ACTION_CLICK
                )
            }

            actual = actual.parent
        }

        return false
    }

}