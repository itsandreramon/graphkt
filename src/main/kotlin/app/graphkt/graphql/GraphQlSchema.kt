/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql

import app.graphkt.concept.SchemaDefinition
import app.graphkt.concept.TypeDefinitions

/**
 * State-holder that represents the current transformation model.
 *
 * @param name Defines the name of generated schema.
 */
class GraphQlSchema(val name: String) {

	private val _types = mutableListOf<GraphQlType>()
	val types: List<GraphQlType> = _types

	fun addType(typeDefinitions: TypeDefinitions, type: GraphQlType) {
		if (typeDefinitions.schemaDefinition.schema == this) {
			_types.add(type)
		} else {
			throw IllegalStateException("Cannot modify types of another schema.")
		}
	}

	fun build(builder: SchemaDefinition.() -> Unit): GraphQlSchema {
		builder(SchemaDefinition(this))
		return this
	}

	override fun toString(): String {
		return """
			name: $name
			types: $types
		""".trimIndent()
	}
}

/**
 * Top-level function used to build a GraphQL schema.
 *
 * @param name The name of the created Schema file.
 * @param builder Lambda passed into in order to add schema properties.
 */
fun buildSchema(name: String, builder: SchemaDefinition.() -> Unit = {}): GraphQlSchema {
	return GraphQlSchema(name).build(builder)
}