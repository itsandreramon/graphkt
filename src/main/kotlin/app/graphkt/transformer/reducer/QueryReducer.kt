/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer.reducer

import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryInput

interface QueryReducer {
    fun reduce(indent: String, queries: List<GraphQlQuery>): String
}

class QueryReducerImpl : QueryReducer {

    override fun reduce(
        indent: String,
        queries: List<GraphQlQuery>,
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