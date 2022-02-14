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
    private val query: GraphQlQuery,
    private val onBuiltCallback: (GraphQlQuery) -> Unit,
) {
    fun build(): QueryBuilder {
        onBuiltCallback(query)
        return this
    }
}

fun QueryDefinitions.Query(name: String, queryBuilder: QueryBuilder.() -> Unit) {
    query.apply {
        this.name = name
    }

    queryBuilder(QueryBuilder(query, onBuiltCallback = {
        schemaDefinition.schema.addQuery(this, it)
    }).build())
}

fun QueryDefinitions.output(output: OutputDefinition.() -> Unit) {
    output(OutputDefinition(this))
}

fun QueryDefinitions.inputs(inputs: InputDefinition.() -> Unit) {
    inputs(InputDefinition(this))
}

/**
 * State-holder that is used to map a given query definition and corresponding schema.
 *
 * @param type The currently defined GraphQL type.
 * @param schemaDefinition The current schema definition the query is being defined on.
 */
class QueryDefinitions(
    val query: GraphQlQuery,
    val schemaDefinition: SchemaDefinition,
)