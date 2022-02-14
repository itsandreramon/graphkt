/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept

import app.graphkt.graphql.GraphQlTypeField

/**
 * Builder that is used to modify a field of a GraphQL type.
 *
 * @param field The field of a GraphQL type that is being modified.
 * @param onBuiltCallback Lambda that is getting executed when all modifications are done.
 */
class FieldBuilder<T : Any>(
    private val field: GraphQlTypeField<T>,
    private val onBuiltCallback: (GraphQlTypeField<T>) -> Unit,
) {
    fun name(value: String) {
        field.name = value
    }

    fun build(): FieldBuilder<T> {
        onBuiltCallback(field)
        return this
    }
}

fun <T : Any> FieldDefinitions.Field(builder: FieldBuilder<T>.() -> Unit) {
    val field = GraphQlTypeField<T>()

    builder(FieldBuilder(field, onBuiltCallback = {
        this.typeDefinitions.type.fields.add(it as GraphQlTypeField<Any>)
    }).build())
}

class FieldDefinitions(
    val typeDefinitions: TypeDefinitions,
)