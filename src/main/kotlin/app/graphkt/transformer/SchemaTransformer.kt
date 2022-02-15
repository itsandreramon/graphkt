/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.transformer.reducer.QueryReducer
import app.graphkt.transformer.util.createIndentOfSize

interface SchemaTransformer {
    fun transform(schema: GraphQlSchema): String
}

class SchemaTransformerImpl(
    private val queryReducer: QueryReducer,
) : SchemaTransformer {

    override fun transform(schema: GraphQlSchema): String {
        return """
            |schema {
            |    type: Query
            |}
            |
            |${transformQueries(schema.queries)}
        """.trimMargin("|")
    }

    private fun transformQueries(queries: List<GraphQlQuery>): String {
        return """
            |type Query {
            |${queryReducer.reduce(indent = createIndentOfSize(4), queries)}
            |}
        """.trimMargin("|")
    }
}