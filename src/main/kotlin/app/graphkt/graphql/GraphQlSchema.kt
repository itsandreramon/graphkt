package app.graphkt.graphql

import app.graphkt.concept.SchemaDefinition

class GraphQlSchema(val name: String) {

	val types: MutableList<GraphQlType> = mutableListOf()

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