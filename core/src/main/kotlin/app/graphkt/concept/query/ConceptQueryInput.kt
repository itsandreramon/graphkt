/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.query

import app.graphkt.concept.AbstractGraphQlBuilder
import app.graphkt.concept.SchemaDefinition
import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryInput

class QueryInputBuilder(
    private val input: GraphQlQueryInput,
    override val onBuiltCallback: (GraphQlQueryInput) -> Unit,
) : AbstractGraphQlBuilder<GraphQlQueryInput>(input, onBuiltCallback) {
    fun name(name: String) {
        input.name = name
    }

    fun type(type: String) {
        input.type = type
    }
}

fun InputDefinitions.Input(builder: QueryInputBuilder.() -> Unit) {
    val input = GraphQlQueryInput()

    builder(QueryInputBuilder(input, onBuiltCallback = {
        this.currentQuery.inputs.add(it)
    }).build() as QueryInputBuilder)
}

class InputDefinitions(
    val schemaDefinition: SchemaDefinition,
    val currentQuery: GraphQlQuery = GraphQlQuery(),
)