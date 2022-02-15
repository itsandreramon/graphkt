/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.query

import app.graphkt.concept.SchemaDefinition
import app.graphkt.graphql.query.GraphQlQuery

class QueryBuilder(
    val query: GraphQlQuery,
    private val onBuiltCallback: (GraphQlQuery) -> Unit,
) {
    fun build(): QueryBuilder {
        onBuiltCallback(query)
        return this
    }
}

fun QueryDefinitions.Query(name: String, queryBuilder: QueryBuilder.() -> Unit) {
    currentQuery = GraphQlQuery().apply {
        this.name = name
    }

    queryBuilder(QueryBuilder(currentQuery, onBuiltCallback = {
        schemaDefinition.schema.addQuery(this, it)
    }).build())
}

fun QueryDefinitions.inputs(inputs: InputDefinitions.() -> Unit) {
    inputs(InputDefinitions(currentQuery))
}

/**
 * State-holder that is used to map a given query definition and corresponding schema.
 *
 * @param type The currently defined GraphQL type.
 * @param schemaDefinition The current schema definition the query is being defined on.
 */
class QueryDefinitions(
    var currentQuery: GraphQlQuery = GraphQlQuery(),
    val schemaDefinition: SchemaDefinition,
)