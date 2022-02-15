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
    fun type(type: String) {
        output.type = type
    }

    fun build(): QueryOutputBuilder {
        onBuiltCallback(output)
        return this
    }
}

fun QueryBuilder.Output(type: String, outputBuilder: QueryOutputBuilder.() -> Unit) {
    query.apply {
        output.type = type
    }

    outputBuilder(QueryOutputBuilder(query.output, onBuiltCallback = {
        query.output = it
    }).build())
}