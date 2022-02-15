/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.query

import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryInput

class QueryInputBuilder(
    private val input: GraphQlQueryInput,
    private val onBuiltCallback: (GraphQlQueryInput) -> Unit,
) {
    fun name(name: String) {
        input.name = name
    }

    fun type(type: String) {
        input.type = type
    }

    fun build(): QueryInputBuilder {
        onBuiltCallback(input)
        return this
    }
}

fun InputDefinitions.Input(builder: QueryInputBuilder.() -> Unit) {
    val input = GraphQlQueryInput()

    builder(QueryInputBuilder(input, onBuiltCallback = {
        this.currentQuery.inputs.add(it)
    }).build())
}

class InputDefinitions(
    val currentQuery: GraphQlQuery = GraphQlQuery(),
)