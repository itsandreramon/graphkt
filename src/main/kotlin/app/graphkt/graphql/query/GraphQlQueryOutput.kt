/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql.query

data class GraphQlQueryOutput(
    var type: String = "",
    val fields: MutableList<GraphQlQuerySelectionField> = mutableListOf(),
    val fragments: MutableList<GraphQlQuerySelectionFragment> = mutableListOf(),
)