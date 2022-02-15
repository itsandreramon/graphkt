/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql.fragment

/**
 * Defines attributes that are used to generate a GraphQL type.
 *
 * @param name The name of the type.
 * @param fields The fields of the type.
 */
data class GraphQlFragment(
    var name: String = "",
    val fields: MutableList<GraphQlFragmentField> = mutableListOf(),
)