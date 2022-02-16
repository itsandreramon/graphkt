/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.type.toInput
import app.graphkt.transformer.reducer.InputReducer
import app.graphkt.transformer.reducer.QueryReducer
import app.graphkt.transformer.reducer.TypeReducer
import app.graphkt.transformer.util.createIndentOfSize

interface SchemaTransformer {
    fun transform(schema: GraphQlSchema): String
}

class SchemaTransformerImpl(
    private val queryReducer: QueryReducer,
    private val typeReducer: TypeReducer,
    private val inputReducer: InputReducer,
) : SchemaTransformer {

    override fun transform(schema: GraphQlSchema): String {
        val inputs = schema.inputs
        val types = schema.types
        val typesWithInput = schema.types.filter { it.generateInput }.map { it.toInput() }

        return """
            |schema {
            |    query: Query
            |}
            |
            |${transformQueries(schema.queries)}
            |
            |${typeReducer.reduce(indent = createIndentOfSize(4), types = types)}
            |
            |${inputReducer.reduce(indent = createIndentOfSize(4), postfix = "Input", inputs = typesWithInput)}
            |
            |${inputReducer.reduce(indent = createIndentOfSize(4), inputs = inputs)}
        """.trimMargin("|")
    }

    private fun transformQueries(queries: List<GraphQlQuery>): String {
        return if (queries.isNotEmpty()) {
            """
            |type Query {
            |${queryReducer.reduce(indent = createIndentOfSize(4), queries)}
            |}
        """.trimMargin("|")
        } else ""
    }
}