/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.type

import app.graphkt.concept.SchemaDefinition
import app.graphkt.graphql.type.GraphQlType

/**
 * Builder that is used to modify a GraphQL type.
 *
 * @param type The GraphQL type that is being modified.
 * @param onBuiltCallback Lambda that is getting executed when all modifications are done.
 */
class TypeBuilder(
    private val type: GraphQlType,
    private val onBuiltCallback: (GraphQlType) -> Unit,
    val typeDefinitions: TypeDefinitions,
) {
    fun generateFragment(value: Boolean) {
        type.generateFragment = value
    }

    fun generateInput(value: Boolean) {
        type.generateInput = value
    }

    fun build(): TypeBuilder {
        onBuiltCallback(type)
        return this
    }
}

/**
 * Scoped function used to build a GraphQL type.
 *
 * @param typeBuilder Lambda passed into in order to define a type.
 */
fun TypeDefinitions.Type(name: String, typeBuilder: TypeBuilder.() -> Unit = {}) {
    val type = GraphQlType().apply {
        this.name = name
    }

    typeBuilder(TypeBuilder(type, onBuiltCallback = {
        schemaDefinition.schema.addType(this, it)
    }, this).build())
}

fun TypeBuilder.fields(fields: TypeFieldDefinitions.() -> Unit) {
    fields(TypeFieldDefinitions(typeDefinitions = typeDefinitions))
}

/**
 * State-holder that is used to map a given type definition and corresponding schema.
 *
 * @param type The currently defined GraphQL type.
 * @param schemaDefinition The current schema definition the type is being defined on.
 */
class TypeDefinitions(
    val schemaDefinition: SchemaDefinition,
)