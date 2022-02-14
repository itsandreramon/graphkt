/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept

import app.graphkt.graphql.GraphQlQueryOutput

class QueryOutputBuilder(
    private val output: GraphQlQueryOutput,
    private val onBuiltCallback: (GraphQlQueryOutput) -> Unit,
) {
    fun build(): QueryOutputBuilder {
        onBuiltCallback(output)
        return this
    }
}

class OutputDefinition(
    val queryDefinitions: QueryDefinitions,
)