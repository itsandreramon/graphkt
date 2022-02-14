/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql

/**
 * Defines attributes that are used to generate a GraphQL type.
 *
 * @param name The name of the type.
 * @param fields The fields of the type.
 * @param generateFragment Determines whether or not a GraphQL fragment is being generated.
 * @param generateInput Determines whether or not a GraphQL input is being generated.
 */
data class GraphQlType(
    var name: String = "",
    var fields: MutableList<GraphQlTypeField<Any>> = mutableListOf(),
    var generateFragment: Boolean = false,
    var generateInput: Boolean = false,
)