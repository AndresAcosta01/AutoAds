package com.andres.autoads.manager

import com.andres.autoads.services.AutoAdsAccessibilityService


object GestureExecutor {


    fun click(
        x: Int,
        y: Int
    ) {


        AutoAdsAccessibilityService
            .instancia
            ?.clickPantalla(
                x,
                y
            )


    }


}