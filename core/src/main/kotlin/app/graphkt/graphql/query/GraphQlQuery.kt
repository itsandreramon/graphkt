/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql.query

import app.graphkt.concept.AbstractGraphQlConcept

data class GraphQlQuery(
    var name: String = "",
    var inputs: MutableList<GraphQlQueryInput> = mutableListOf(),
    var output: GraphQlQueryOutput = GraphQlQueryOutput(),
) : AbstractGraphQlConcept() {
    val capitalizedName: String
        get() = name.replaceFirstChar { it.uppercase() }
}