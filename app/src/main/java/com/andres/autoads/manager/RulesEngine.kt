package com.andres.autoads.manager

object RulesEngine {

    private val reglas = mutableListOf<Rule>()

    init {

        reglas.add(
            Rule(
                nombre = "X",
                texto = "✕"
            )
        )

        reglas.add(
            Rule(
                nombre = "Multiplicación",
                texto = "×"
            )
        )

        reglas.add(
            Rule(
                nombre = "Mayor",
                texto = ">"
            )
        )

        reglas.add(
            Rule(
                nombre = "Doble Mayor",
                texto = ">>"
            )
        )

        reglas.add(
            Rule(
                nombre = "Flecha Derecha",
                texto = "❯"
            )
        )

        reglas.add(
            Rule(
                nombre = "Chevron",
                texto = "›"
            )
        )

        reglas.add(
            Rule(
                nombre = "Siguiente",
                texto = "⏭"
            )
        )

        reglas.add(
            Rule(
                nombre = "Play",
                texto = "▶"
            )
        )

    }

    fun obtenerReglas(): List<Rule> {
        return reglas
    }

}