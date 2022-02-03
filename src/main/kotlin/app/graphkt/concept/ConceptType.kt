/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept

import app.graphkt.graphql.GraphQlType

/**
 * Builder that is used to modify a GraphQL type.
 *
 * @param type The GraphQL type that is being modified.
 * @param onBuiltCallback Lambda that is getting executed when all modifications are done.
 */
class TypeBuilder(
	private val type: GraphQlType,
	private val onBuiltCallback: (GraphQlType) -> Unit,
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
	this.type.apply {
		this.name = name
	}

	typeBuilder(TypeBuilder(type, onBuiltCallback = {
		this.schemaDefinition.schema.addType(this, it)
	}).build())
}

fun TypeDefinitions.fields(fields: FieldDefinitions.() -> Unit) {
	fields(FieldDefinitions(this))
}

/**
 * State-holder that is used to map a given type definition and corresponding schema.
 *
 * @param type The currently defined GraphQL type.
 * @param schemaDefinition The current schema definition the type is being defined on.
 */
class TypeDefinitions(
	val type: GraphQlType,
	val schemaDefinition: SchemaDefinition,
)