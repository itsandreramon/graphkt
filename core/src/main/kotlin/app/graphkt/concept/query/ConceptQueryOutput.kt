/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.query

import app.graphkt.concept.AbstractGraphQlBuilder
import app.graphkt.graphql.query.GraphQlQueryOutput

class QueryOutputBuilder(
    private val output: GraphQlQueryOutput,
    override val onBuiltCallback: (GraphQlQueryOutput) -> Unit,
) : AbstractGraphQlBuilder<GraphQlQueryOutput>(output, onBuiltCallback) {
    fun type(type: String) {
        output.type = type
    }
}

fun QueryBuilder.Output(type: String, outputBuilder: QueryOutputBuilder.() -> Unit = {}) {
    this.query.apply {
        output.type = type
    }

    outputBuilder(QueryOutputBuilder(query.output, onBuiltCallback = {
        this.query.output = it
    }).build() as QueryOutputBuilder)
}