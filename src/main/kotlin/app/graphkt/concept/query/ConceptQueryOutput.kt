/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.query

import app.graphkt.graphql.query.GraphQlQueryOutput

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