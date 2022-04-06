/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql.input

data class GraphQlInput(
    var name: String = "",
    var fields: MutableList<GraphQlInputField> = mutableListOf(),
)