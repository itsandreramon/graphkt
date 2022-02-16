/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer.reducer

import app.graphkt.graphql.input.GraphQlInput
import app.graphkt.transformer.util.createIndentOfSize

interface InputReducer {
    fun reduce(inputs: List<GraphQlInput>, indent: String = createIndentOfSize(4), postfix: String = ""): String
}

class InputReducerImpl(
    private val inputFieldReducer: InputFieldReducer,
) : InputReducer {

    override fun reduce(
        inputs: List<GraphQlInput>,
        indent: String,
        postfix: String,
    ): String {
        return buildString {
            inputs.onEach { input ->
                append("""
                    |input ${input.name}${postfix} {
                    |${inputFieldReducer.reduce(input.fields, indent)}
                    |}
                """.trimMargin("|"))

                append("\n")
            }
        }
    }
}