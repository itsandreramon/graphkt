/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.type.toFragment
import app.graphkt.graphql.type.toInput
import app.graphkt.transformer.reducer.FragmentReducer
import app.graphkt.transformer.reducer.InputReducer
import app.graphkt.transformer.reducer.QueryReducer
import app.graphkt.transformer.reducer.TypeReducer

interface SchemaTransformer {
    fun transform(schema: GraphQlSchema): String
}

class SchemaTransformerImpl(
    private val queryReducer: QueryReducer,
    private val typeReducer: TypeReducer,
    private val inputReducer: InputReducer,
    private val fragmentReducer: FragmentReducer,
) : SchemaTransformer {

    override fun transform(schema: GraphQlSchema): String {
        val inputs = schema.inputs
        val types = schema.types
        val fragments = schema.fragments

        val typesWithFragment = schema.types
            .filter { it.generateFragment }
            .map { it.toFragment() }

        val typesWithInput = schema.types
            .filter { it.generateInput }
            .map { it.toInput() }

        return """
            |schema {
            |    query: Query
            |}
            |
            |${transformQueries(schema.queries)}
            |
            |${typeReducer.reduce(types)}
            |
            |${inputReducer.reduce(typesWithInput, postfix = "Input")}
            |
            |${inputReducer.reduce(inputs)}
            |
            |${fragmentReducer.reduce(typesWithFragment, postfix = "Fragment")}
            |
            |${fragmentReducer.reduce(fragments)}
        """.trimMargin("|")
    }

    private fun transformQueries(queries: List<GraphQlQuery>): String {
        return if (queries.isNotEmpty()) {
            """
            |type Query {
            |${queryReducer.reduce(queries)}
            |}
        """.trimMargin("|")
        } else ""
    }
}