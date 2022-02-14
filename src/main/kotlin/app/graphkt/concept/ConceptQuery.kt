/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept

import app.graphkt.graphql.GraphQlQuery

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

class QueryDefinitions(
    val query: GraphQlQuery,
    val schemaDefinition: SchemaDefinition,
)