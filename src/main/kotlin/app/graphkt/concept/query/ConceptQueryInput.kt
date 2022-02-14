/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.query

import app.graphkt.graphql.query.GraphQlQueryInput

class QueryInputBuilder(
    private val input: GraphQlQueryInput,
    private val onBuiltCallback: (GraphQlQueryInput) -> Unit,
) {
    fun name(name: String) {
        input.name = name
    }

    fun build(): QueryInputBuilder {
        onBuiltCallback(input)
        return this
    }
}

fun InputDefinition.Input(builder: QueryInputBuilder.() -> Unit) {
    val input = GraphQlQueryInput()

    builder(QueryInputBuilder(input, onBuiltCallback = {
        this.queryDefinitions.query.inputs.add(it)
    }).build())
}

class InputDefinition(
    val queryDefinitions: QueryDefinitions,
)