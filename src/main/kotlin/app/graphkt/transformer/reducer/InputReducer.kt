/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer.reducer

import app.graphkt.graphql.input.GraphQlInput

interface InputReducer {
    fun reduce(indent: String, postfix: String = "", inputs: List<GraphQlInput>): String
}

class InputReducerImpl(
    private val inputFieldReducer: InputFieldReducer,
) : InputReducer {

    override fun reduce(indent: String, postfix: String, inputs: List<GraphQlInput>): String {
        return buildString {
            inputs.onEach { input ->
                append("""
                    |input ${input.name}${postfix} {
                    |${inputFieldReducer.reduce(indent, input.fields)}
                    |}
                """.trimMargin("|"))
            }
        }
    }
}