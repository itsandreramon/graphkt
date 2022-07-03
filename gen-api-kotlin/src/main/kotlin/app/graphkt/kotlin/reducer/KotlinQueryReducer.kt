/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.kotlin.reducer

import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryInput
import app.graphkt.transformer.reducer.QueryReducer
import app.graphkt.util.createIndentOfSize

interface KotlinQueryReducer : QueryReducer {
    fun reduceAsSignature(queries: List<GraphQlQuery>, indent: String = createIndentOfSize(4)): String
}

class KotlinQueryReducerImpl : KotlinQueryReducer {
    override fun reduce(queries: List<GraphQlQuery>, indent: String): String {
        return buildString {
            queries.onEach { query ->
                append("""
                    |override ${getQuerySignatureAsString(query)} {
                    |    return apollo.query(${query.capitalizedName}()).execute()
                    |}
                """.trimMargin("|"))
            }
        }
    }

    override fun reduceAsSignature(queries: List<GraphQlQuery>, indent: String): String {
        return buildString {
            queries.onEach { query ->
                append("""
                    |${getQuerySignatureAsString(query)}
                """.trimMargin("|"))
            }
        }
    }

    private fun getQuerySignatureAsString(query: GraphQlQuery): String {
        return "suspend fun ${query.name}(${getQueryInputParameters(query)}): ${query.capitalizedName}.Data"
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