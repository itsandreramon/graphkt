/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.query

import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryOutput

class QueryOutputBuilder(
    private val output: GraphQlQueryOutput,
    private val onBuiltCallback: (GraphQlQueryOutput) -> Unit,
) {
    fun type(type: String) {
        output.type = type
    }

    fun build(): QueryOutputBuilder {
        onBuiltCallback(output)
        return this
    }
}

fun QueryDefinitions.Output(type: String, outputBuilder: QueryOutputBuilder.() -> Unit) {
    currentQuery = GraphQlQuery().apply {
        this.output.type = type
    }

    outputBuilder(QueryOutputBuilder(currentQuery.output, onBuiltCallback = {
        currentQuery.output = it
    }).build())
}