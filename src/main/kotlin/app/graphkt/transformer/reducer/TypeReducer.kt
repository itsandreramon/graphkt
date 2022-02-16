/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer.reducer

import app.graphkt.graphql.type.GraphQlType
import app.graphkt.transformer.util.createIndentOfSize

interface TypeReducer {
    fun reduce(types: List<GraphQlType>, indent: String = createIndentOfSize(4)): String
}

class TypeReducerImpl(
    private val typeFieldReducer: TypeFieldReducer,
) : TypeReducer {

    override fun reduce(types: List<GraphQlType>, indent: String): String {
        return buildString {
            types.onEach { type ->
                append("""
                    |type ${type.name} {
                    |${typeFieldReducer.reduce(indent, type.fields)}
                    |}
                """.trimMargin("|"))

                append("\n")
            }
        }
    }
}