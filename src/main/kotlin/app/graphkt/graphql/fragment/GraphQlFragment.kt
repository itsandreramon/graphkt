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
 * @param generateFragment Determines whether or not a GraphQL fragment is being generated.
 * @param generateInput Determines whether or not a GraphQL input is being generated.
 */
data class GraphQlFragment(
    var name: String = "",
    val fields: MutableList<GraphQlFragmentField> = mutableListOf(),
)