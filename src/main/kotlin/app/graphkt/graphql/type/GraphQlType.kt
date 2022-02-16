/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql.type

import app.graphkt.graphql.fragment.GraphQlFragment
import app.graphkt.graphql.fragment.GraphQlFragmentField
import app.graphkt.graphql.input.GraphQlInput
import app.graphkt.graphql.input.GraphQlInputField

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
    var fields: MutableList<GraphQlTypeField> = mutableListOf(),
    var generateFragment: Boolean = false,
    var generateInput: Boolean = false,
)

fun GraphQlType.toFragment(): GraphQlFragment {
    return GraphQlFragment(
        name = this.name,
        type = this.name,
        fields = this.fields.map { it.toFragmentField() }.toMutableList(),
    )
}

fun GraphQlTypeField.toFragmentField(): GraphQlFragmentField {
    return GraphQlFragmentField(
        name = this.name,
        type = this.type,
    )
}

fun GraphQlType.toInput(): GraphQlInput {
    return GraphQlInput(
        name = this.name,
        fields = this.fields.map { it.toInputField() }.toMutableList(),
    )
}

fun GraphQlTypeField.toInputField(): GraphQlInputField {
    return GraphQlInputField(
        name = this.name,
        type = this.type,
    )
}