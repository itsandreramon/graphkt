/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer.reducer

import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryInput
import app.graphkt.util.createIndentOfSize

interface QueryReducer {
    fun reduce(queries: List<GraphQlQuery>, indent: String = createIndentOfSize(4)): String
}

class QueryReducerImpl : QueryReducer {

    override fun reduce(
        queries: List<GraphQlQuery>,
        indent: String,
    ): String {
        return buildString {
            queries.onEachIndexed { index, query ->
                append("${indent}${query.name}(${getQueryInputs(query)}): ${query.output.type}")

                if (index != (queries.size - 1)) {
                    append("\n")
                }
            }
        }
    }

    private fun getQueryInputs(query: GraphQlQuery): String {
        fun getQueryInput(input: GraphQlQueryInput): String {
            return "${input.name}: ${input.type}"
        }

        return query.inputs.joinToString(
            separator = ", ",
            transform = ::getQueryInput,
        )
    }
}