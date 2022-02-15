/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.type

import app.graphkt.graphql.type.GraphQlType
import app.graphkt.graphql.type.GraphQlTypeField

/**
 * Builder that is used to modify a field of a GraphQL type.
 *
 * @param field The field of a GraphQL type that is being modified.
 * @param onBuiltCallback Lambda that is getting executed when all modifications are done.
 */
class TypeFieldBuilder(
    private val field: GraphQlTypeField,
    private val onBuiltCallback: (GraphQlTypeField) -> Unit,
) {
    fun name(value: String) {
        field.name = value
    }

    fun type(value: String) {
        field.type = value
    }

    fun build(): TypeFieldBuilder {
        onBuiltCallback(field)
        return this
    }
}

fun TypeFieldDefinitions.Field(builder: TypeFieldBuilder.() -> Unit) {
    val field = GraphQlTypeField()

    builder(TypeFieldBuilder(field, onBuiltCallback = {
        this.currentType.fields.add(it)
    }).build())
}

data class TypeFieldDefinitions(
    val currentType: GraphQlType = GraphQlType(),
)