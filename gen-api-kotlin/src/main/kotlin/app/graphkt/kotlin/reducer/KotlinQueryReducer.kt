/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.kotlin.reducer

import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryInput
import app.graphkt.kotlin.KotlinQueryReduction
import app.graphkt.transformer.util.createIndentOfSize

interface KotlinQueryReducer {
    fun reduce(queries: List<GraphQlQuery>, indent: String = createIndentOfSize(4)): KotlinQueryReduction
}

class KotlinQueryReducerImpl : KotlinQueryReducer {

    override fun reduce(queries: List<GraphQlQuery>, indent: String): KotlinQueryReduction {
        return KotlinQueryReduction(buildString {
            queries.onEach { query ->
                append("""
                    |override suspend fun ${query.name}(${getQueryInputParameters(query)}): ${query.capitalizedName}.Data {
                    |    return apollo.query(${query.capitalizedName}()).execute()
                    |}
                """.trimMargin("|"))
            }
        })
    }

    private fun getQueryInputParameters(query: GraphQlQuery): String {
        fun getQueryInput(input: GraphQlQueryInput): String {
            return "${input.name}: ${input.type}"
        }

        return query.inputs.joinToString(
            separator = ", ",
            transform = ::getQueryInput,
        )
    }
}