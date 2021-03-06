/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer.reducer

import app.graphkt.graphql.input.GraphQlInputField

interface InputFieldReducer {
    fun reduce(fields: List<GraphQlInputField>, indent: String): String
}

class InputFieldReducerImpl : InputFieldReducer {
    override fun reduce(fields: List<GraphQlInputField>, indent: String): String {
        return buildString {
            fields.onEachIndexed { index, field ->
                append("${indent}${field.name}: ${field.type}")

                if (index != (fields.size - 1)) {
                    append("\n")
                }
            }
        }
    }
}
