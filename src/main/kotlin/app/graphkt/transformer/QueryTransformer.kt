/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer

import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryInput
import app.graphkt.transformer.reducer.QuerySelectionReducer

interface QueryTransformer {
    fun transform(query: GraphQlQuery): String
}

class QueryTransformerImpl(
    private val querySelectionReducer: QuerySelectionReducer,
) : QueryTransformer {

    override fun transform(query: GraphQlQuery): String {
        return buildString {
            append(transformQuery(query) + "\n")
        }
    }

    private fun transformQuery(query: GraphQlQuery): String {
        val name = query.name.replaceFirstChar { it.uppercase() }

        return """
            |query $name(${getQueryInputs(query)}) {
            |   ${query.name}(${getQueryInputVariables(query)}) {
            |${querySelectionReducer.reduce(query)}
            |   }
            |}
            |
        """.trimMargin("|")
    }

    private fun getQueryInputs(query: GraphQlQuery): String {
        fun getQueryInput(input: GraphQlQueryInput): String {
            return "$${input.name}: ${input.type}"
        }

        return query.inputs.joinToString(
            separator = ", ",
            transform = ::getQueryInput,
        )
    }

    private fun getQueryInputVariables(query: GraphQlQuery): String {
        fun getQueryInput(input: GraphQlQueryInput): String {
            return "${input.name}: $${input.name}"
        }

        return query.inputs.joinToString(
            separator = ", ",
            transform = ::getQueryInput,
        )
    }
}