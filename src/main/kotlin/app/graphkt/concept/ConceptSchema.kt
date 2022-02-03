package app.graphkt.concept

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.graphql.GraphQlType

/**
 * Function used to define types inside a TypeDefinitions scope.
 *
 * @param types Lambda passed into in order to add types.
 */
fun SchemaDefinition.types(types: TypeDefinitions.() -> Unit) {
	types(TypeDefinitions(GraphQlType(), this))
}

/**
 * State-holder that is used to save the current schema.
 *
 * @param schema The current schema that is going to be defined.
 */
class SchemaDefinition(val schema: GraphQlSchema)